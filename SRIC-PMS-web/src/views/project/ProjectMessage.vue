<template>
    <el-button type="info" class="project-message-back-button" @click="clickBack" plain>
        <el-icon size="20">
            <Back/>
        </el-icon>
        <p class="project-message-back-button-text">{{ $t("common.back") }}</p>
    </el-button>
    <div class="common-body-set-width">
        <span class="project-message-card-name">{{ projectData.name }}</span>
        <div class="project-message-card-message-div">
            <span style="margin-right: 10px;">{{ projectData.createTime }}</span>
            <span>{{ projectData.description }}</span>
            <div class="project-message-card-message-icon">
                <el-icon v-if="projectData.isEnded == 0" style="color: #67C23A;"><Unlock/></el-icon>
                <el-icon v-if="projectData.isEnded == 1" style="color: #F56C6C;"><Lock/></el-icon>
            </div>
        </div>
        <el-avatar v-for="(item,i) in projectData.userList" :key="i" :src="avatarUrlBefore + item.avatar" class="project-message-card-message-avatar"></el-avatar>
        <el-divider style="margin: 20px 0px 10px 0px;"></el-divider>
        <el-scrollbar :height="scrollbarHeight">
            <div class="project-message-item-body-div">
                <el-button style="margin: 0px 0px 10px 10px;" type="primary" @click="clickAddProjectItem"><el-icon size="15"><Plus /></el-icon></el-button>
                <el-button style="margin: 0px 0px 10px 10px;" type="success" @click="clickChangeMode" plain><el-icon size="15"><Finished /></el-icon></el-button>
                <el-timeline v-if="!isTable">
                    <el-timeline-item v-if="isAdd">
                        <el-card>
                            <el-form ref="projectItemFormRef" :model="projectItemForm" :rules="projectItemFormRules" label-width="auto">
                                <el-form-item prop="title">
                                    <template #label>
                                        {{ $t("common.title") }}
                                    </template>
                                    <el-input v-model="projectItemForm.title" clearable show-word-limit maxlength="50"></el-input>
                                </el-form-item>
                                <el-divider></el-divider>
                                <el-form-item prop="text">
                                    <template #label>
                                        {{ $t("common.content") }}
                                    </template>
                                    <el-input v-model="projectItemForm.text" clearable show-word-limit maxlength="300" type="textarea" :autosize="{ minRows: 3 }"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="success" plain @click="clickAddProjectItemConfirm(projectItemFormRef)">
                                        <el-icon><Check /></el-icon>
                                    </el-button>
                                    <el-button type="info" plain @click="clickCancelAdd()">
                                        <el-icon><Close /></el-icon>
                                    </el-button>
                                </el-form-item>
                            </el-form>
                        </el-card>
                    </el-timeline-item>
                    <el-timeline-item v-for="(item,i) in projectItemData" :key="i" center>
                        <div class="project-message-user-message-div">
                            <el-avatar :src="avatarUrlBefore + item.createUserAvatar"></el-avatar>
                            <p class="project-message-user-message-text">{{ item.createUserName }} {{ $t("static.createAt") }} {{ item.createTime }}</p>
                        </div>
                        <el-card>
                            <span class="project-message-item-title">{{ item.title }}</span>
                            <el-divider></el-divider>
                            <span class="project-message-item-text-span">{{ item.text }}</span>
                        </el-card>
                        <div class="project-message-item-footer-div">
                            <el-card>
                                <div class="project-message-user-message-div">
                                    <el-avatar v-if="!(item.assignUserAvatar == null)" @click="clickPickUser(item)" :src="avatarUrlBefore + item.assignUserAvatar"></el-avatar>
                                    <p v-if="!(item.assignUserAvatar == null)" @click="clickPickUser(item)" class="project-message-user-message-text">{{ item.assignUserName }}</p>
                                    <p v-if="item.assignUserAvatar == null" @click="clickPickUser(item)" class="project-message-user-message-text">{{ $t("static.noMemberAssigned") }}</p>
                                    <el-divider class="project-message-item-footer-divider" direction="vertical" />
                                    <el-tooltip
                                        effect="dark"
                                        placement="top-end"
                                    >
                                        <template #content>
                                            <span class="project-message-item-footer-tooltip">{{ $t("static.notFinished") }}</span>
                                        </template>
                                        <el-icon @click="clickEnd(item)" v-if="item.isEnded == 0" style="color: #E6A23C;" size="20"><Warning /></el-icon>
                                    </el-tooltip>
                                    <el-tooltip
                                        effect="dark"
                                        placement="top-end"
                                    >
                                        <template #content>
                                            <span class="project-message-item-footer-tooltip">{{ $t("static.isFinished") }}</span>
                                        </template>
                                        <el-icon v-if="item.isEnded == 1" style="color: #67C23A;" size="20"><SuccessFilled /></el-icon>
                                    </el-tooltip>
                                </div>
                            </el-card>
                        </div>
                    </el-timeline-item>
                </el-timeline>
                <el-table v-if="isTable" :data="projectItemData">
                    <el-table-column prop="createUserName">
                        <template #header>
                            创建者
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime">
                        <template #header>
                            创建时间
                        </template>
                    </el-table-column>
                    <el-table-column prop="title">
                        <template #header>
                            任务
                        </template>
                    </el-table-column>
                    <el-table-column prop="text">
                        <template #header>
                            内容
                        </template>
                    </el-table-column>
                    <el-table-column prop="assignUserAvatar">
                        <template #header>
                            负责人
                        </template>
                        <template #default="scope">
                            <p v-if="!(scope.row.assignUserAvatar == null)" @click="clickPickUser(scope.row)" class="project-message-user-message-text">{{ scope.row.assignUserName }}</p>
                            <p v-if="scope.row.assignUserAvatar == null" @click="clickPickUser(scope.row)" class="project-message-user-message-text">{{ $t("static.noMemberAssigned") }}</p>
                        </template>
                    </el-table-column>
                    <el-table-column prop="isEnded">
                        <template #header>
                            状态
                        </template>
                        <template #default="scope">
                            <el-tooltip
                                        effect="dark"
                                        placement="top-end"
                                    >
                                        <template #content>
                                            <span class="project-message-item-footer-tooltip">{{ $t("static.notFinished") }}</span>
                                        </template>
                                        <el-icon @click="clickEnd(scope.row)" v-if="scope.row.isEnded == 0" style="color: #E6A23C;" size="20"><Warning /></el-icon>
                                    </el-tooltip>
                                    <el-tooltip
                                        effect="dark"
                                        placement="top-end"
                                    >
                                        <template #content>
                                            <span class="project-message-item-footer-tooltip">{{ $t("static.isFinished") }}</span>
                                        </template>
                                        <el-icon v-if="scope.row.isEnded == 1" style="color: #67C23A;" size="20"><SuccessFilled /></el-icon>
                                    </el-tooltip>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-scrollbar>
        <div class="project-message-pagination-div">
            <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
        </div>
    </div>
    <el-drawer v-model="isEndDrawerOpen" direction="ttb">
        <template #header>
            <h1>{{ $t("common.confirm") }}</h1>
        </template>
        <template #default>
            <div class="project-message-end-confirm-div">
                <div class="project-message-end-confirm-text">{{ $t("static.endConfirm") }}</div>
                <div class="project-message-end-confirm-title">{{ endItemTitle }}</div>
            </div>
        </template>
        <template #footer>
            <el-button type="info" plain @click="clickCancelEnd()">{{ $t("common.no") }}</el-button>
            <el-button type="danger" @click="doEndItem()">{{ $t("common.yes") }}</el-button>
        </template>
    </el-drawer>
    <el-drawer v-model="isPickUserDrawerOpen">
        <template #header>
            <h1>{{ $t("common.choose") }}</h1>
        </template>
        <template #default>
            <div class="project-message-pick-user-div">
                <el-card class="project-message-pick-user-card" v-for="(item,i) in projectData.userList" :key="i" @mouseover="mouseOverUser(i)" @mouseleave="mouseLeaveUser(i)" :style="item.background" @click="doPickUser(item)">
                    <div class="project-message-user-message-div">
                        <el-avatar :src="avatarUrlBefore + item.avatar"></el-avatar>
                        <p class="project-message-user-message-text">{{ item.username }}</p>
                    </div>
                </el-card>
            </div>
        </template>
    </el-drawer>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref,onMounted,reactive } from 'vue'
import { getProjectByUuid,getProjectItemListByProject,endProjectItem,pickProjectItemUser,addProjectItem } from '@/axios/api/project'
import { useRouter,useRoute } from "vue-router";
import { useDark } from '@vueuse/core'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus' //element消息
import i18n from '@/language';
const { t } = i18n.global

const route = useRoute()
const router = useRouter()

const isDark = useDark()
const projectData:any = ref([]) //项目数据
const projectItemData:any = ref({}) //项目内容数据
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const scrollbarHeight = ref((window.innerHeight - 340) + "px") //设置滚动条高度
const avatarUrlBefore = ref(axios.defaults.baseURL + "/static/file/userAvatar/") //用户头像路径前缀
const isEndDrawerOpen = ref(false) //用于完成的确认抽屉是否打开
const isPickUserDrawerOpen = ref(false) //用于选择用户的抽屉是否打开
const endItemTitle = ref("") //当前选择完成的 项目条目标题
const endItemUuid = ref("") //当前选择完成的 项目条目 uuid
const pickUserItemUuid = ref("") //当前选择指派的 项目条目 uuid
const isAdd = ref(false) //是否显示 添加项目条目 时间轴
const isTable = ref(false) //是否为表格视图

const projectItemForm = reactive //添加 projectItem 的表单
({
    title: "",
    text: "",
})
const projectItemFormRef = ref<FormInstance>() //添加 projectItem 表单的 ref
const projectItemFormRules = reactive //添加 projectItem 表单的 rule
({
    title:
    [
        { required: true, message: t("rules.title"), trigger: 'blur' },
    ],
    text:
    [
        { required: true, message: t("rules.content"), trigger: 'blur' },
    ],
})

const getProjectData = async () => //获取项目数据
{
    try
    {
        const params =
        {
            uuid: route.query.project,
        }
        const resp = await getProjectByUuid(params)
        projectData.value = resp.data || {} //若数据被拦截 data 为 undefined 则使用空对象
    } catch {}
}

const getProjectItem = async () => //获取项目内容数据
{
    try
    {
        const params =
        {
            projectUuid: route.query.project,
            page: page.value,
        }
        const resp = await getProjectItemListByProject(params)
        projectItemData.value = resp.data.projectItemList
        pageTotal.value = resp.data.total
    } catch {}
}

onMounted( () => 
{
    getProjectData()
    getProjectItem()
})

const clickBack = () => //点击返回
{
    router.push
    ({
        name: 'Project',
        path: '/Project',
    })
}

const clickChangeMode = () =>
{
    isTable.value = !isTable.value
}

const clickAddProjectItem = () => //点击新增 项目条目
{
    isAdd.value = true
}

const clickAddProjectItemConfirm = async (formEl: FormInstance | undefined) => //点击新增 projectItem 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doAddProjectItem()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const doAddProjectItem = async () => //发送新增 projectItem 请求
{
    try
    {
        const params =
        {
            title: projectItemForm.title,
            text: projectItemForm.text,
            projectUuid: projectData.value.uuid,
        }
        const resp = await addProjectItem(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.addSuccess"),
                type: 'success',
            })
            getProjectData()
            getProjectItem()
            isAdd.value = false
            projectItemForm.title = ""
            projectItemForm.text = ""
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.addFailed"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 401) //当前用户未加入当前项目
        {
            ElMessage({
                message: t("static.CanNotChangeNotInsideProject"),
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
    } catch {}
}

const clickCancelAdd = () => //取消新增
{
    isAdd.value = false
    projectItemForm.title = ""
    projectItemForm.text = ""
}

const clickPickUser = (item:any) => //点击指派成员
{
    pickUserItemUuid.value = item.uuid
    isPickUserDrawerOpen.value = true
}

const clickEnd = (item:any) => //点击未完成图标
{
    endItemTitle.value = item.title
    endItemUuid.value = item.uuid
    isEndDrawerOpen.value = true
}

const clickCancelEnd = () => //点击取消 完成
{
    isEndDrawerOpen.value = false
}

const doEndItem = async () => //点击确认 完成
{
    try
    {
        const params =
        {
            uuid: endItemUuid.value,
            projectUuid: projectData.value.uuid,
        }
        const resp = await endProjectItem(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.submitSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.submitFailed"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 401) //当前用户未加入当前项目
        {
            ElMessage({
                message: t("static.CanNotChangeNotInsideProject"),
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
        getProjectData()
        getProjectItem()
        isEndDrawerOpen.value = false
    } catch {}
}

const mouseOverUser = (i:any) => //鼠标移动到用户卡片中
{
    if(isDark.value == false)
    {
        projectData.value.userList[i].background = "background: #E5EAF3"
    }
    else
    {
        projectData.value.userList[i].background = "background: #303030"
    }
}

const mouseLeaveUser = (i:any) => //鼠标移出用户卡片
{
    projectData.value.userList[i].background = ""
}

const doPickUser = async (item:any) => //选择指派成员
{
    try
    {
        const params =
        {
            uuid: pickUserItemUuid.value,
            projectUuid: projectData.value.uuid,
            assignUserUuid: item.uuid,
        }
        const resp = await pickProjectItemUser(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.submitSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.submitFailed"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 401) //当前用户未加入当前项目
        {
            ElMessage({
                message: t("static.CanNotChangeNotInsideProject"),
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
        getProjectData()
        getProjectItem()
        isPickUserDrawerOpen.value = false
    } catch {}
}

const pageChange = () => //翻页
{
    getProjectData()
}

const resetScrollbarHeight = () => //重置滚动条高度(适应窗口大小)
{
    scrollbarHeight.value = (window.innerHeight - 340) + "px"
}

resetScrollbarHeight()

window.addEventListener('resize',resetScrollbarHeight) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.project-message-back-button
{
    margin-bottom: 10px;
}

.project-message-back-button-text
{
    margin-left: 5px;
}

.project-message-card-name
{
    font-weight: bold;
    font-size: 25px;
}

.project-message-card-message-div
{
    margin: 5px 0px 5px 0px;
}

.project-message-card-message-icon
{
    float: right;
}

.project-message-card-message-avatar
{
    margin: 5px 0px 0px 5px;
}

.project-message-item-body-div
{
    margin: 10px;
}

.project-message-item-body-div .el-divider--horizontal
{
    margin-top: 5px;
    margin-bottom: 10px;
}

.project-message-user-message-div
{
    display: flex;
    align-items: center;
}

.project-message-user-message-text
{
    font-size: 18px;
    font-weight: bold;
    margin-left: 10px;
}

.project-message-item-title
{
   font-weight: bold;
   font-size: 25px; 
}

.project-message-item-text-span
{
    font-size: 15px;
    font-weight: bold;
}

.project-message-item-footer-div
{
    display: flex;
    justify-content: flex-end; /* 让内容从右向左排列 */
    align-items: center; /* 垂直方向居中对齐 */
    margin-top: 10px;
}

.project-message-item-footer-div .el-card__body
{
    padding: 0px 20px 0px 20px;
}

.project-message-item-footer-divider
{
    margin: 0px 20px 0px 20px;
}

.project-message-item-footer-tooltip
{
    font-size: 12px;
    font-weight: bold;
}

.project-message-pagination-div
{
    margin-top: 20px;
}

.project-message-end-confirm-div
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

.project-message-end-confirm-text
{
    font-weight: bold;
    font-size: 30px;
}

.project-message-end-confirm-title
{
    font-weight: bold;
    font-size: 20px;
    margin-top: 10px;
}

.project-message-pick-user-div .el-card__body
{
    padding: 0px 20px 0px 20px;
}

.project-message-pick-user-card
{
    margin-top: 10px;
}
</style>