<template>
    <el-button type="info" class="project-item-manage-back-button" @click="clickBack" plain>
        <el-icon size="20">
            <Back/>
        </el-icon>
        <p class="project-item-manage-back-button-text">{{ $t("common.back") }}</p>
    </el-button>
    <div class="common-body-set-width">
        <span class="project-item-manage-card-name">{{ projectData.name }}</span>
        <div class="project-item-manage-card-message-div">
            <span style="margin-right: 10px;">{{ projectData.createTime }}</span>
            <span>{{ projectData.description }}</span>
            <div class="project-item-manage-card-message-icon">
                <el-icon v-if="projectData.isEnded == 0" style="color: #67C23A;"><Unlock/></el-icon>
                <el-icon v-if="projectData.isEnded == 1" style="color: #F56C6C;"><Lock/></el-icon>
            </div>
        </div>
        <el-avatar v-for="(item,i) in projectData.userList" :key="i" :src="avatarUrlBefore + item.avatar" class="project-item-manage-card-message-avatar"></el-avatar>
        <el-divider style="margin: 20px 0px 10px 0px;"></el-divider>
        <el-scrollbar :height="scrollbarHeight">
            <div class="project-item-manage-item-body-div">
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
                        <div class="project-item-manage-user-message-div">
                            <el-avatar :src="avatarUrlBefore + item.createUserAvatar"></el-avatar>
                            <p class="project-item-manage-user-message-text">{{ item.createUserName }} {{ $t("static.createAt") }} {{ item.createTime }}</p>
                        </div>
                        <el-card>
                            <el-button @click="clickEditProjectItem(item)" type="primary" style="float: right;" plain>
                                <el-icon size="15"><Edit /></el-icon>
                            </el-button>
                            <span class="project-item-manage-item-title">{{ item.title }}</span>
                            <el-divider></el-divider>
                            <span class="project-item-manage-item-text-span">{{ item.text }}</span>
                        </el-card>
                        <div class="project-item-manage-item-footer-div">
                            <el-card>
                                <div class="project-item-manage-user-message-div">
                                    <el-avatar v-if="!(item.assignUserAvatar == null)" @click="clickPickUser(item)" :src="avatarUrlBefore + item.assignUserAvatar"></el-avatar>
                                    <p v-if="!(item.assignUserAvatar == null)" @click="clickPickUser(item)" class="project-item-manage-user-message-text">{{ item.assignUserName }}</p>
                                    <p v-if="item.assignUserAvatar == null" @click="clickPickUser(item)" class="project-item-manage-user-message-text">{{ $t("static.noMemberAssigned") }}</p>
                                    <el-divider class="project-item-manage-item-footer-divider" direction="vertical" />
                                    <el-popover
                                        placement="left-start"
                                    >
                                        <template #reference>
                                            <el-icon v-if="item.isEnded == 0" style="color: #E6A23C;" size="20"><Warning /></el-icon>
                                            <el-icon v-if="item.isEnded == 1" style="color: #67C23A;" size="20"><SuccessFilled /></el-icon>
                                        </template>
                                        <el-button @click="clickChangeEnded(item,1)" type="success" link>
                                            <el-icon style="margin-right: 10px;" size="15"><SuccessFilled /></el-icon>
                                            {{ $t("static.isFinished") }}
                                        </el-button>
                                        <el-divider style="margin: 10px 0px 10px 0px;"></el-divider>
                                        <el-button @click="clickChangeEnded(item,0)" type="warning" link>
                                            <el-icon style="margin-right: 10px;" size="15"><Warning /></el-icon>
                                            {{ $t("static.notFinished") }}
                                        </el-button>
                                    </el-popover>
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
                            <el-popover
                                        placement="left-start"
                                    >
                                        <template #reference>
                                            <el-icon v-if="scope.row.isEnded == 0" style="color: #E6A23C;" size="20"><Warning /></el-icon>
                                            <el-icon v-if="scope.row.isEnded == 1" style="color: #67C23A;" size="20"><SuccessFilled /></el-icon>
                                        </template>
                                        <el-button @click="clickChangeEnded(scope.row,1)" type="success" link>
                                            <el-icon style="margin-right: 10px;" size="15"><SuccessFilled /></el-icon>
                                            {{ $t("static.isFinished") }}
                                        </el-button>
                                        <el-divider style="margin: 10px 0px 10px 0px;"></el-divider>
                                        <el-button @click="clickChangeEnded(scope.row,0)" type="warning" link>
                                            <el-icon style="margin-right: 10px;" size="15"><Warning /></el-icon>
                                            {{ $t("static.notFinished") }}
                                        </el-button>
                                    </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-scrollbar>
        <div class="project-item-manage-pagination-div">
            <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
        </div>
    </div>
    <el-drawer v-model="isEditDrawerOpen" direction="ttb">
        <template #header>
            <h1>{{ $t("common.confirm") }}</h1>
        </template>
        <template #default>
            <div class="project-item-manage-delete-confirm-div">
                <el-form ref="editProjectItemFormRef" :model="projectItemForm" :rules="projectItemFormRules" label-width="auto">
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
                </el-form>
            </div>
        </template>
        <template #footer>
            <el-button type="info" plain @click="clickCancelEdit()">{{ $t("common.no") }}</el-button>
            <el-button type="danger" @click="clickEditProjectItemConfirm(editProjectItemFormRef)">{{ $t("common.yes") }}</el-button>
        </template>
    </el-drawer>
    <el-drawer v-model="isPickUserDrawerOpen">
        <template #header>
            <h1>{{ $t("common.choose") }}</h1>
        </template>
        <template #default>
            <div class="project-item-manage-pick-user-div">
                <el-card class="project-item-manage-pick-user-card" v-for="(item,i) in projectData.userList" :key="i" @mouseover="mouseOverUser(i)" @mouseleave="mouseLeaveUser(i)" :style="item.background" @click="doPickUser(item)">
                    <div class="project-item-manage-user-message-div">
                        <el-avatar :src="avatarUrlBefore + item.avatar"></el-avatar>
                        <p class="project-item-manage-user-message-text">{{ item.username }}</p>
                    </div>
                </el-card>
            </div>
        </template>
    </el-drawer>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref,onMounted,reactive } from 'vue'
import { getProjectByUuid,getProjectItemListByProject,endProjectItemManage,pickProjectItemUserManage,addProjectItemManage,updateProjectItemTextManage } from '@/axios/api/project'
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
const isEditDrawerOpen = ref(false) //用于编辑的抽屉是否打开
const isPickUserDrawerOpen = ref(false) //用于选择用户的抽屉是否打开
const editItemUuid = ref("") //当前选择修改数据的 项目条目 uuid
const pickUserItemUuid = ref("") //当前选择指派的 项目条目 uuid
const isAdd = ref(false) //是否显示 添加项目条目 时间轴
const isTable = ref(false) //是否为表格视图

const projectItemForm = reactive //projectItem 的表单
({
    title: "",
    text: "",
})
const projectItemFormRef = ref<FormInstance>() //添加 projectItem 表单的 ref
const editProjectItemFormRef = ref<FormInstance>() //编辑 projectItem 表单的 ref
const projectItemFormRules = reactive //projectItem 表单的 rule
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
        name: 'ProjectItemManageIndex',
        path: '/ProjectItemManageIndex',
    })
}

const clickChangeMode = () =>
{
    isTable.value = !isTable.value
}

const clickAddProjectItem = () => //点击新增 项目条目
{
    projectItemForm.title = ""
    projectItemForm.text = ""
    isEditDrawerOpen.value = false //设置修改抽屉关闭
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
        const resp = await addProjectItemManage(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.addSuccess"),
                type: 'success',
            })
            getProjectData()
            getProjectItem()
            isAdd.value = false
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

const clickChangeEnded = async (item:any,value:any) => //点击改变状态
{
    try
    {
        const params =
        {
            uuid: item.uuid,
            isEnded: value,
        }
        const resp = await endProjectItemManage(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.editSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.editFailed"),
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
    } catch {}
}

const clickEditProjectItem = (item:any) => //点击编辑
{
    editItemUuid.value = item.uuid
    projectItemForm.title = item.title
    projectItemForm.text = item.text
    isAdd.value = false //设置添加卡片隐藏
    isEditDrawerOpen.value = true
}

const clickCancelEdit = () => //点击取消 编辑
{
    isEditDrawerOpen.value = false
}

const clickEditProjectItemConfirm = async (formEl: FormInstance | undefined) => //点击编辑 projectItem 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doEditItem()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const doEditItem = async () => //点击确认 编辑
{
    try
    {
        const params =
        {
            uuid: editItemUuid.value,
            title: projectItemForm.title,
            text: projectItemForm.text,
        }
        const resp = await updateProjectItemTextManage(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.editSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.editFailed"),
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
        isEditDrawerOpen.value = false
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
        const resp = await pickProjectItemUserManage(params)
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

.project-item-manage-back-button
{
    margin-bottom: 10px;
}

.project-item-manage-back-button-text
{
    margin-left: 5px;
}

.project-item-manage-card-name
{
    font-weight: bold;
    font-size: 25px;
}

.project-item-manage-card-message-div
{
    margin: 5px 0px 5px 0px;
}

.project-item-manage-card-message-icon
{
    float: right;
}

.project-item-manage-card-message-avatar
{
    margin: 5px 0px 0px 5px;
}

.project-item-manage-item-body-div
{
    margin: 10px;
}

.project-item-manage-item-body-div .el-divider--horizontal
{
    margin-top: 5px;
    margin-bottom: 10px;
}

.project-item-manage-user-message-div
{
    display: flex;
    align-items: center;
}

.project-item-manage-user-message-text
{
    font-size: 18px;
    font-weight: bold;
    margin-left: 10px;
}

.project-item-manage-item-title
{
   font-weight: bold;
   font-size: 25px; 
}

.project-item-manage-item-text-span
{
    font-size: 15px;
    font-weight: bold;
}

.project-item-manage-item-footer-div
{
    display: flex;
    justify-content: flex-end; /* 让内容从右向左排列 */
    align-items: center; /* 垂直方向居中对齐 */
    margin-top: 10px;
}

.project-item-manage-item-footer-div .el-card__body
{
    padding: 0px 20px 0px 20px;
}

.project-item-manage-item-footer-divider
{
    margin: 0px 20px 0px 20px;
}

.project-item-manage-item-footer-tooltip
{
    font-size: 12px;
    font-weight: bold;
}

.project-item-manage-pagination-div
{
    margin-top: 20px;
}

.project-item-manage-delete-confirm-div
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

.project-item-manage-delete-confirm-text
{
    font-weight: bold;
    font-size: 30px;
}

.project-item-manage-delete-confirm-title
{
    font-weight: bold;
    font-size: 20px;
    margin-top: 10px;
}

.project-item-manage-pick-user-div .el-card__body
{
    padding: 0px 20px 0px 20px;
}

.project-item-manage-pick-user-card
{
    margin-top: 10px;
}
</style>