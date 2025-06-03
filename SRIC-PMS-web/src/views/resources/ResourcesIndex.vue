<template>
    <div class="common-body-set-width">
        <el-container>
            <el-aside :width="asideWidth">
                <el-menu
                :style="minHeight"
                :default-active="deafultActiveMenu"
                :ellipsis="false"
                :collapse="isCollapse"
                @select="selectMenu"
                >
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
                                <el-button class="resources-card-download-button" type="success" @click="clickDownloadResources(item)">
                                    <el-icon :size="18">
                                        <Download />
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
    </div>
    <el-drawer v-model="isDownloadMessageDrawerOpen" direction="ttb">
        <template #header>
            <h1>{{ $t("static.downloadFile") }}</h1>
        </template>
        <template #default>
            <div class="resources-delete-confirm-div">
                <div class="resources-delete-confirm-name">
                    {{ $t("common.download") }} {{ downloadFileMessage.name }}
                    <span class="resources-delete-confirm-name-type">{{ downloadFileMessage.type }}</span>
                </div>
                <div class="resources-delete-confirm-title">
                    <el-tag
                    class="resources-card-tags"
                    effect="dark"
                    type="warning"
                    size="small"
                    >
                        <span class="resources-card-tag-text">{{ downloadFileMessage.categoryName }}</span>
                    </el-tag>
                    <span class="resources-manage-card-span">{{ downloadFileMessage.size }}MB</span>
                </div>
            </div>
        </template>
        <template #footer>
            <el-button type="danger" @click="doDownload()" :loading="isDownloadButtonLoading">
                <el-icon :size="18" style="margin-right: 5px;">
                    <Download />
                </el-icon>
                {{ $t("common.download") }}
            </el-button>
        </template>
    </el-drawer>
</template>

<script lang="ts" setup>
import { ref,onMounted,h } from 'vue'
import { getResourcesCategoryList,getResourcesList,downloadResources } from '@/axios/api/resources';
import { downloadFileFromResponse } from '@/tools/tool'
import { useRouter } from "vue-router";
import { ElNotification } from 'element-plus' //element
import i18n from '@/language';
const { t } = i18n.global

const router = useRouter()

const deafultActiveMenu = "0" //访问页面时默认菜单选项
const minHeight = ref("min-height:" + (window.innerHeight - 200) + "px;" )//设置菜单栏高度
const isCollapse = ref(false) //菜单是否折叠
const asideWidth = ref("200px") //侧边栏宽度

const isDownloadMessageDrawerOpen = ref(false) //下载文件的抽屉是否打开
const downloadFileMessage:any = ref({}) //当前下载文件的信息
const isDownloadButtonLoading:any = ref(false) //下载按钮是否加载
const resourcesCategory:any = ref([]) //resources 类别数据
const resourcesCategorySelected = ref("0") //当前选择的 resources 类别
const scrollbarHeight = ref((window.innerHeight - 225) + "px") //设置滚动条高度
const searchInput = ref() //搜索输入内容
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const resourcesList:any = ref([]) //resources 数据列表

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
        const resp = await getResourcesList(params)
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

const clickDownloadResources = (item:any) => //点击下载 resources
{
    isDownloadMessageDrawerOpen.value = true
    downloadFileMessage.value = item
}

const doDownload = async () =>
{
    isDownloadButtonLoading.value = true //设置按钮为加载状态
    isDownloadMessageDrawerOpen.value = false //关闭抽屉
    ElNotification //提示下载开始
    ({
        title: t("common.noties"),
        message: h('i', { style: 'color: #409EFF' }, t("static.downloadHasStarted")),
        offset: 50,
    })

    try
    {
        const params =
        {
            uuid: downloadFileMessage.value.uuid,
        }
        const resp = await downloadResources(params)

        downloadFileFromResponse(resp)

        isDownloadButtonLoading.value = false

    } catch {}
}

const pageChange = () => //翻页
{
    getResourcesData()
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
    scrollbarHeight.value = (window.innerHeight - 225) + "px"
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

.resources-card-download-button
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
    margin: 0px 5px 4px 5px;
}

.resources-drawer-title
{
    font-weight: bold;
}

.resources-submit-button-icon
{
    margin-right: 10px;
}

.resources-delete-confirm-div
{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.resources-delete-confirm-name
{
    font-weight: bold;
    font-size: 30px;
}

.resources-delete-confirm-name-type
{
    font-weight: bold;
    font-size: 20px;
}

.resources-delete-confirm-title
{
    font-weight: bold;
    font-size: 20px;
    margin-top: 10px;
}
</style>