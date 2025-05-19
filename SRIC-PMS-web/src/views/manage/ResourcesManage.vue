<template>
    <el-card>
        <el-container>
            <el-aside :width="asideWidth">
                <el-menu
                :style="minHeight"
                :default-active="deafultActiveMenu"
                :ellipsis="false"
                :collapse="isCollapse"
                @select="selectMenu"
                >
                    <el-menu-item @click="clickCollapse">
                        <el-icon :size="25"><Switch /></el-icon>
                        <template #title>
                            <span class="resources-category-item-span">{{ $t("common.collapse") }}</span>
                        </template>
                    </el-menu-item>
                    <el-menu-item index="0">
                        <el-icon :size="25"><Coin /></el-icon>
                        <template #title>
                            <span class="resources-category-item-span">{{ $t("common.all") }}</span>
                        </template>
                    </el-menu-item>
                    <el-menu-item v-for="(item,i) in resourcesCategory" :key="i" :index="item.id.toString()">
                        <el-icon :size="25"><Menu /></el-icon>
                        <template #title>
                            <span class="resources-category-item-span">{{ item.name }}</span>
                        </template>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <div>
                    <div class="resources-search-div">
                        <el-row>
                            <el-col :span="20">
                                <el-input
                                v-model="searchInput"
                                placeholder="search"
                                >
                                <template #append>
                                    <el-button @click="clickSearch">
                                        <el-icon>
                                            <search/>
                                        </el-icon>
                                    </el-button>
                                </template>
                                </el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-button @click="clickUploadResources()" class="resources-add-button" type="danger">
                                    <el-icon size="15">
                                        <UploadFilled/>
                                    </el-icon>
                                </el-button>
                            </el-col>
                        </el-row>
                    </div>
                    <el-scrollbar :height="scrollbarHeight">
                        <el-card
                            v-for="(item,i) in resourcesList"
                            :key="i"
                            @mouseover="mouseOver(i)"
                            @mouseleave="mouseLeave(i)"
                            :style="item.background"
                            class="resources-card"
                            >
                            <div class="resources-card-details">
                                <span class="resources-card-name-text">{{ item.name }}</span>
                                <el-button class="resources-card-edit-button" type="warning" @click="clickEditResources(item)">
                                    <el-icon>
                                        <Edit />
                                    </el-icon>
                                </el-button>
                                <div class="resources-card-tags-div">
                                    <span class="resources-manage-card-span">{{ item.type }}</span>
                                    <span class="resources-manage-card-span">{{ item.size }}MB</span>
                                    <el-tag
                                    class="resources-card-tags"
                                    effect="dark"
                                    type="warning"
                                    size="small"
                                    >
                                        <span class="resources-card-tag-text">{{ item.categoryName }}</span>
                                    </el-tag>
                                    <span class="resources-manage-card-span" :style="getStatusColor(item.level)">{{ $t("common.level") }} {{ item.level }}</span>
                                    <span class="resources-manage-card-span">{{ item.createTime }}</span>
                                </div>
                            </div>
                        </el-card>
                    </el-scrollbar>
                    <div class="resources-pagination-div">
                        <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
                    </div>
                </div>
            </el-main>
        </el-container>
    </el-card>
    <el-drawer
    v-model="editDialogVisible"
    >
        <template #header>
            <span class="resources-drawer-title">{{ $t("static.editResources") }}</span>
        </template>
        <el-form ref="resourcesFormRef" :model="resourcesForm" :rules="resourcesFormRules">
            <el-form-item prop="name">
                <h1>{{ $t("common.name") }}</h1>
                <el-input v-model="resourcesForm.name" clearable></el-input>
            </el-form-item>
            <el-form-item prop="level">
                <h1>{{ $t("static.permissionLevel") }}</h1>
                <el-select v-model="resourcesForm.level">
                    <el-option v-for="(item,i) in levels" :key="i" :label="item.label" :value="item.value">
                        <el-tag :color="item.label" class="permission-main-menu-select-color-tag" size="small" />
                        <span :style="{ color: item.label }">{{ $t("static.permissionLevel") }} : {{ item.value }}</span>
                    </el-option>
                    <template #label="{ value }">
                        <span>{{ $t("static.permissionLevel") }} : {{ value }}</span>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item prop="category">
                <h1>{{ $t("common.category") }}</h1> 
                <el-select v-model="resourcesForm.category">
                    <el-option v-for="(item,i) in resourcesCategory" :key="i" :label="item.name" :value="item.id" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="clickEditResourcesMessage(resourcesFormRef)" type="success">
                    <el-icon class="resources-submit-button-icon" size="15">
                        <CircleCheckFilled/>
                    </el-icon>
                    {{ $t("common.submit") }}
                </el-button>
            </el-form-item>
        </el-form>
    </el-drawer>
</template>

<script lang="ts" setup>
import { ref,onMounted,reactive } from 'vue'
import { getResourcesCategoryList,getResourcesManageList,editResourcesMessage } from '@/axios/api/resources';
import { getStatusColor } from '@/tools/tool'
import { levels } from '@/tools/staticResources' //引入权限静态数据
import { useRouter } from "vue-router";
import { ElMessage } from 'element-plus' //element消息
import type { FormInstance } from 'element-plus'
import i18n from '@/language';
const { t } = i18n.global

const router = useRouter()

const deafultActiveMenu = "0" //访问页面时默认菜单选项
const minHeight = ref("min-height:" + (window.innerHeight - 200) + "px;" )//设置菜单栏高度
const isCollapse = ref(false) //菜单是否折叠
const asideWidth = ref("200px") //侧边栏宽度

const resourcesCategory:any = ref([]) //resources 类别数据
const resourcesCategorySelected = ref("0") //当前选择的 resources 类别
const scrollbarHeight = ref((window.innerHeight - 320) + "px") //设置滚动条高度
const searchInput = ref() //搜索输入内容
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const resourcesList:any = ref([]) //resources 数据列表
const editDialogVisible = ref(false) //修改 resources 的抽屉是否开启
const editUuid = ref("") //当前修改的 resources uuid
const resourcesForm = reactive //修改 resources 的表单
({
    name: "",
    level: "",
    category: "",
})
const resourcesFormRef = ref<FormInstance>() //修改 resources 表单的 ref
const resourcesFormRules = reactive //修改 resources 表单的 rule
({
    name:
    [
        { required: true, message: t("rules.name"), trigger: 'blur' },
    ],
    level:
    [
        { required: true, message: t("rules.permissionLevel"), trigger: 'blur' },
    ],
    category:
    [
        { required: true, message: t("rules.category"), trigger: 'blur' },
    ],
})

const getResourcesCategoryData = async () => //获取 resources 类别数据
{
    try
    {
        const resp = await getResourcesCategoryList({})
        resourcesCategory.value = resp.data
    } catch {}
}

const getResourcesData = async () =>
{
    try
    {
        const params = 
        {
            searchInput: searchInput.value,
            category: resourcesCategorySelected.value,
            page: page.value,
        }
        const resp = await getResourcesManageList(params)
        pageTotal.value = resp.data.total
        resourcesList.value = resp.data.resourcesList
    } catch {}
}

onMounted( () =>
{
    getResourcesCategoryData()
    getResourcesData()
})

const selectMenu = (key) => //类别菜单选择
{
    resourcesCategorySelected.value = key
    getResourcesData()
}

const mouseOver = (i:any) => //鼠标移入
{
    resourcesList.value[i].background = "border-width: 5px;"
}

const mouseLeave = (i:any) => //鼠标移出
{
    resourcesList.value[i].background = ""
}

const clickSearch = () => //点击搜索
{
    getResourcesData()
}

const clickUploadResources = () => //点击上传资源
{
    router.push
    ({
        name: 'ResourcesUpload',
        path: '/ResourcesUpload',
    })
}

const clickEditResources = (item:any) => //点击编辑 resources
{
    editUuid.value = item.uuid
    resourcesForm.name = item.name
    resourcesForm.level = item.level
    resourcesForm.category = item.category
    editDialogVisible.value = true
}

const pageChange = () => //翻页
{
    getResourcesData()
}

const clickEditResourcesMessage = async (formEl: FormInstance | undefined) => //修改 resources 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doEditResources()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const doEditResources = async () =>
{
    try
    {
        const params =
        {
            uuid: editUuid.value,
            name: resourcesForm.name,
            level: resourcesForm.level,
            category: resourcesForm.category,
        }
        const resp = await editResourcesMessage(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.saveSuccess"),
                type: 'success',
            })
            editDialogVisible.value = false
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.nameHasBeenUsed"),
                type: 'warning',
            })
        }
        else
        {
            ElMessage({
                message: t("static.paramsError"),
                type: 'error',
            })
        }
        editDialogVisible.value = false
        getResourcesData()
    } catch {}
}

const clickCollapse = () => //点击折叠菜单
{
    if(window.innerWidth > 800) //在窗口不被强制折叠时可以使用
    {
        if(isCollapse.value)
        {
            isCollapse.value = false
            asideWidth.value = "200px"
        }
        else
        {
            isCollapse.value = true
            asideWidth.value = "75px"
        }
    }
}

const resetMinHeightAndMenu = () =>
{
    minHeight.value = "min-height:" + (window.innerHeight - 200) + "px;" //重置菜单栏高度(适应窗口大小)
    if(window.innerWidth < 800) //在窗口过小时折叠菜单栏
    {
        isCollapse.value = true
        asideWidth.value = "75px"
    }
    else
    {
        isCollapse.value = false
        asideWidth.value = "200px"
    }
    resetScrollbarHeight()
}

const resetScrollbarHeight = () => //重置滚动条高度(适应窗口大小)
{
    scrollbarHeight.value = (window.innerHeight - 310) + "px"
}

resetMinHeightAndMenu()

window.addEventListener('resize',resetMinHeightAndMenu) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.resources-category-item-span
{
    font-size: 20px;
    font-weight: bold;
}

.resources-search-div
{
    margin-bottom: 10px;
}

.resources-add-button
{
    float: right;
}

.resources-card
{
    border-color: #aaaaaa;
    margin-bottom: 10px;
}

.resources-card-details
{
    margin: 0;
}

.resources-card-name-text
{
    font-size: 30px;
    font-weight: bold;
    margin: 0;
}

.resources-card-edit-button
{
    float: right;
}

.resources-card-tags-div
{
    margin: 10px 0px 0px 0px;
}

.resources-card-tag-text
{
    font-weight: bold;
}

.resources-pagination-div
{
    margin-top: 10px;
}

.resources-manage-card-span
{
    margin: 0px 5px 0px 5px;
}

.resources-card-tags
{
    margin: 0px 5px 2px 5px;
}

.resources-drawer-title
{
    font-weight: bold;
}

.resources-submit-button-icon
{
    margin-right: 10px;
}
</style>