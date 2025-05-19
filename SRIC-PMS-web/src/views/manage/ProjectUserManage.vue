<template>
    <el-card>
        <el-scrollbar :height="scrollbarHeight">
            <el-card v-for="(item,i) in projectData" :key="i" class="project-user-card">
                <span class="project-user-card-name">{{ item.name }}</span>
                <div class="project-user-card-message-div">
                    <span style="margin-right: 10px;">{{ item.createTime }}</span>
                    <span>{{ item.description }}</span>
                    <div class="project-user-card-message-icon">
                        <el-icon v-if="item.isEnded == 0" style="color: #67C23A;"><Unlock/></el-icon>
                        <el-icon v-if="item.isEnded == 1" style="color: #F56C6C;"><Lock/></el-icon>
                    </div>
                </div>
                <div class="project-user-card-users-div" v-for="(userItem,j) in item.userList" :key="j" @mouseover="mouseOverUser(i,j)" @mouseleave="mouseLeaveUser(i,j)" :style="userItem.background">
                    <el-avatar class="project-user-card-users-avatar" :src="avatarUrlBefore + userItem.avatar"></el-avatar>
                    <p class="project-user-card-users-name">{{ userItem.username }}</p>
                    <div style="flex-grow: 10;"></div>
                    <el-button class="project-user-card-users-button" @click="clickDeleteMember(item,userItem)" type="danger" round>
                        <el-icon size="15"><Delete /></el-icon>
                        <span>{{ $t("static.deleteMember") }}</span>
                    </el-button>
                </div>
                <el-button class="project-user-add-user-button" @click="clickAddMember(item)" type="warning" plain>
                    <el-icon size="15"><Plus /></el-icon>
                    <span>{{ $t("static.addMember") }}</span>
                </el-button>
            </el-card>
        </el-scrollbar>
        <div class="project-user-pagination-div">
            <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
        </div>
    </el-card>
    <el-drawer v-model="isChooseUserDrawerOpen">
        <template #header>
            <span class="project-user-drawer-title">{{ $t("static.addMember") }}</span>
        </template>
        <h1>{{ $t("common.search") }} - {{ $t("static.usernameOrMail") }}</h1>
        <el-input v-model="searchUserKeyWord">
            <template #append>
                <el-button @click="clickSearchUser">
                    <el-icon size="15"><Search /></el-icon>
                </el-button>
            </template>
        </el-input>
        <el-card class="project-user-search-data-card" v-for="(item,i) in searchUserData" :key="i">
            <el-button link class="project-user-search-data-user-name" @click="clickChooseUser(item)">{{ item.username }}</el-button>
            <p class="project-user-search-data-user-mail">{{ item.mail }}</p>
        </el-card>
    </el-drawer>
    <el-drawer v-model="isDeleteUserDrawerOpen" direction="ttb">
        <template #header>
            <h1>{{ $t("common.warning") }}</h1>
        </template>
        <template #default>
            <div class="project-user-delete-confirm-div">
                <div class="project-user-delete-confirm-text">{{ $t("static.deleteMemberConfirm") }}</div>
                <div class="project-user-delete-confirm-title">{{ deleteUsername }}</div>
            </div>
        </template>
        <template #footer>
            <el-button type="info" plain @click="clickCancelDelete()">{{ $t("common.no") }}</el-button>
            <el-button type="danger" @click="doDeleteNote()">{{ $t("common.yes") }}</el-button>
        </template>
    </el-drawer>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref,onMounted } from 'vue'
import { getProjectManageListWithUserList,deleteProjectUser,addProjectUser } from '@/axios/api/project'
import { getUserListByKeyWord } from '@/axios/api/user'
import { useDark } from '@vueuse/core'
import { ElMessage } from 'element-plus' //element消息
import i18n from '@/language';
const { t } = i18n.global

const isDark = useDark()
const projectData:any = ref({}) //项目数据
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const avatarUrlBefore = ref(axios.defaults.baseURL + "/static/file/userAvatar/") //用户头像路径前缀
const scrollbarHeight = ref((window.innerHeight - 240) + "px") //设置滚动条高度
const isChooseUserDrawerOpen = ref(false) //选择成员的抽屉是否打开
const isDeleteUserDrawerOpen = ref(false) //移除成员的抽屉是否打开
const deleteUsername = ref("") //要移除的用户名
const deleteUserUuid = ref("") //要移除的用户 uuid
const deleteUserProjectUuid = ref("") //要移除成员的项目 uuid
const searchUserKeyWord = ref("") //添加成员时输入的搜索内容
const searchUserData:any = ref([]) //添加成员时搜索到的成员内容
const addUserProjectUuid = ref("") //要添加成员的项目 uuid

const getProjectData = async () => //获取项目数据
{
    try
    {
        const params =
        {
            page: page.value,
        }
        const resp = await getProjectManageListWithUserList(params)
        projectData.value = resp.data.projectList
        pageTotal.value = resp.data.total
    } catch {}
}

onMounted( () =>
{
    getProjectData()
})

const mouseOverUser = (i:any,j:any) => //鼠标移动到用户卡片中
{
    if(isDark.value == false)
    {
        projectData.value[i].userList[j].background = "background: #E5EAF3"
    }
    else
    {
        projectData.value[i].userList[j].background = "background: #303030"
    }
}

const mouseLeaveUser = (i:any,j:any) => //鼠标移出用户卡片
{
    projectData.value[i].userList[j].background = ""
}

const clickDeleteMember = (item:any,userItem:any) => //点击移除成员
{
    deleteUsername.value = userItem.username
    deleteUserUuid.value = userItem.uuid
    deleteUserProjectUuid.value = item.uuid
    isDeleteUserDrawerOpen.value = true
}

const clickAddMember = (item:any) => //点击添加成员
{
    addUserProjectUuid.value = item.uuid
    isChooseUserDrawerOpen.value = true
}

const pageChange = () => //翻页
{
    getProjectData()
}

const clickSearchUser = async () => //点击搜索用户
{
    try
    {
        const params =
        {
            keyWord: searchUserKeyWord.value,
        }
        const resp = await getUserListByKeyWord(params)
        searchUserData.value = resp.data
    } catch{}
}

const clickChooseUser = async (item:any) => //点击选择用户 加入项目
{
    try
    {
        const params =
        {
            userUuid: item.uuid,
            projectUuid: addUserProjectUuid.value,
        }
        const resp = await addProjectUser(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.addSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.memberHasBeenAdded"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 500)
        {
            ElMessage({
                message: t("static.addFailed"),
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
        isChooseUserDrawerOpen.value = false
        getProjectData()
    } catch {}
}

const clickCancelDelete = () => //点击取消移除
{
    isDeleteUserDrawerOpen.value = false
}

const doDeleteNote = async () => //点击确认移除用户
{
    try
    {
        const params =
        {
            userUuid: deleteUserUuid.value,
            projectUuid: deleteUserProjectUuid.value,
        }
        const resp = await deleteProjectUser(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.removeSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.removeFailed"),
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
        isDeleteUserDrawerOpen.value = false
        getProjectData()
    } catch {}
}

const resetScrollbarHeight = () => //重置滚动条高度(适应窗口大小)
{
    scrollbarHeight.value = (window.innerHeight - 240) + "px"
}

resetScrollbarHeight()

window.addEventListener('resize',resetScrollbarHeight) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.project-user-card
{
    margin-top: 10px;
}

.project-user-card-name
{
    font-weight: bold;
    font-size: 25px;
}

.project-user-card-message-div
{
    margin: 5px 0px 5px 0px;
}

.project-user-card-message-icon
{
    float: right;
}

.project-user-card-users-div
{
    display: flex;
    align-items: center;
    border-radius: 4px;
    margin-top: 5px;
}

.project-user-card-users-avatar
{
    margin-left: 20px;
}

.project-user-card-users-name
{
    font-weight: bold;
    margin-left: 10px;
}

.project-user-card-users-button
{
    margin-right: 10px;
}

.project-user-add-user-button
{
    margin: 10px 0px 0px 10px;
}

.project-user-pagination-div
{
    margin-top: 20px;
}

.project-user-drawer-title
{
    font-weight: bold;
}

.project-user-search-data-card
{
    margin-top: 5px;
    background: #E5EAF3;
}

html.dark .project-user-search-data-card
{
    background: #303030;
}

.project-user-search-data-card .el-card__body
{
    padding: 10px;
}

.project-user-search-data-user-name
{
    margin: 0;
    font-weight: bold;
    font-size: 20px;
}

.project-user-search-data-user-mail
{
    margin: 0;
    font-size: 15px;
}

.project-user-delete-confirm-div
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

.project-user-delete-confirm-text
{
    font-weight: bold;
    font-size: 30px;
}

.project-user-delete-confirm-title
{
    font-weight: bold;
    font-size: 20px;
    margin-top: 10px;
}
</style>