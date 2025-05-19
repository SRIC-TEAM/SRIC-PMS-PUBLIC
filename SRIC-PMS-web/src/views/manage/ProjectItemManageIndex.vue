<template>
    <el-card>
        <el-scrollbar :height="scrollbarHeight">
            <el-card
            v-for="(item,i) in projectData"
            :key="i"
            class="project-item-card"
            @mouseover="mouseOver(i)"
            @mouseleave="mouseLeave(i)"
            @click="clickProject(item)"
            :style="item.background"
            >
                <span class="project-item-card-name">{{ item.name }}</span>
                <div class="project-item-card-message-div">
                    <span style="margin-right: 10px;">{{ item.createTime }}</span>
                    <span>{{ item.description }}</span>
                    <div class="project-item-card-message-icon">
                        <el-icon v-if="item.isEnded == 0" style="color: #67C23A;"><Unlock/></el-icon>
                        <el-icon v-if="item.isEnded == 1" style="color: #F56C6C;"><Lock/></el-icon>
                    </div>
                </div>
            </el-card>
        </el-scrollbar>
        <div class="project-item-pagination-div">
            <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
        </div>
    </el-card>
</template>

<script lang="ts" setup>
import { ref,onMounted } from 'vue'
import { getProjectManageList } from '@/axios/api/project';
import { useRouter } from "vue-router";

const router = useRouter()

const projectData:any = ref({}) //项目数据
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const scrollbarHeight = ref((window.innerHeight - 240) + "px") //设置滚动条高度

const getProjectData = async () => //获取项目数据
{
    try
    {
        const params =
        {
            page: page.value,
        }
        const resp = await getProjectManageList(params)
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

const clickProject = (item:any) =>
{
    router.push
    ({
        name: 'ProjectItemManage',
        path: '/ProjectItemManage',
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
    scrollbarHeight.value = (window.innerHeight - 240) + "px"
}

resetScrollbarHeight()

window.addEventListener('resize',resetScrollbarHeight) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.project-item-card
{
    margin-top: 10px;
    border-color: #aaaaaa;
}

.project-item-card-name
{
    font-weight: bold;
    font-size: 25px;
}

.project-item-card-message-div
{
    margin: 5px 0px 5px 0px;
}

.project-item-card-message-icon
{
    float: right;
}

.project-item-pagination-div
{
    margin-top: 20px;
}
</style>