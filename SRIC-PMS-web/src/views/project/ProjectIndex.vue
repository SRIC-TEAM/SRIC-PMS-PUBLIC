<template>
    <div class="common-body-set-width">
        <el-card>
            <el-scrollbar :height="scrollbarHeight">
                <el-card
                v-for="(item,i) in projectData"
                :key="i"
                class="project-index-card"
                @mouseover="mouseOver(i)"
                @mouseleave="mouseLeave(i)"
                @click="clickProject(item)"
                :style="item.background"
                >
                    <span class="project-index-card-name">{{ item.name }}</span>
                    <div class="project-index-card-message-div">
                        <span style="margin-right: 10px;">{{ item.createTime }}</span>
                        <span>{{ item.description }}</span>
                        <div class="project-index-card-message-icon">
                            <el-icon v-if="item.isEnded == 0" style="color: #67C23A;"><Unlock/></el-icon>
                            <el-icon v-if="item.isEnded == 1" style="color: #F56C6C;"><Lock/></el-icon>
                        </div>
                    </div>
                    <el-avatar v-for="(userItem,j) in item.userList" :key="j" :src="avatarUrlBefore + userItem.avatar" class="project-index-card-message-avatar"></el-avatar>
                </el-card>
            </el-scrollbar>
            <div class="project-index-pagination-div">
                <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
            </div>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref,onMounted } from 'vue'
import { getProjectListWithUserList } from '@/axios/api/project';
import { useRouter } from "vue-router";

const router = useRouter()

const projectData:any = ref({}) //项目数据
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const scrollbarHeight = ref((window.innerHeight - 200) + "px") //设置滚动条高度
const avatarUrlBefore = ref(axios.defaults.baseURL + "/static/file/userAvatar/") //用户头像路径前缀

const getProjectData = async () => //获取项目数据
{
    try
    {
        const params =
        {
            page: page.value,
        }
        const resp = await getProjectListWithUserList(params)
        projectData.value = resp.data.projectList
        pageTotal.value = resp.data.total
    } catch {}
}

onMounted( () =>
{
    getProjectData()
})

const mouseOver = (i:any) => //鼠标移入
{
    projectData.value[i].background = "border-width: 5px;"
}

const mouseLeave = (i:any) => //鼠标移出
{
    projectData.value[i].background = ""
}

const clickProject = (item:any) => //点击项目卡片
{
    router.push
    ({
        name: 'ProjectMessage',
        path: '/ProjectMessage',
        query:
        {
            project: item.uuid
        },
    })
}

const pageChange = () => //翻页
{
    getProjectData()
}

const resetScrollbarHeight = () => //重置滚动条高度(适应窗口大小)
{
    scrollbarHeight.value = (window.innerHeight - 200) + "px"
}

resetScrollbarHeight()

window.addEventListener('resize',resetScrollbarHeight) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.project-index-card
{
    margin-top: 10px;
    border-color: #aaaaaa;
}

.project-index-card-name
{
    font-weight: bold;
    font-size: 25px;
}

.project-index-card-message-div
{
    margin: 5px 0px 5px 0px;
}

.project-index-card-message-icon
{
    float: right;
}

.project-index-card-message-avatar
{
    margin: 5px 0px 0px 5px;
}

.project-index-pagination-div
{
    margin-top: 20px;
}
</style>