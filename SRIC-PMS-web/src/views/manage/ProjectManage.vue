<template>
    <el-card>
        <el-table :data="tableData" :height="tableHeight">
            <el-table-column prop="uuid" width="200">
                <template #header>
                    PROJECT ID
                </template>
            </el-table-column>
            <el-table-column prop="name" width="200">
                <template #header>
                    {{ $t("common.name") }}
                </template>
            </el-table-column>
            <el-table-column prop="createTime" width="180">
                <template #header>
                    {{ $t("common.createTime") }}
                </template>
            </el-table-column>
            <el-table-column prop="description">
                <template #header>
                    {{ $t("common.description") }}
                </template>
            </el-table-column>
            <el-table-column prop="isEnded" width="60">
                <template #header>
                    {{ $t("common.state") }}
                </template>
                <template #default="scope">
                    <el-icon v-if="scope.row.isEnded == 0" style="color: #67C23A;"><Unlock/></el-icon>
                    <el-icon v-if="scope.row.isEnded == 1" style="color: #F56C6C;"><Lock/></el-icon>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations" width="120">
                <template #header>
                    {{ $t("common.operations") }}
                </template>
                <template #default="scope">
                    <el-button link type="primary" @click="clickEdit(scope.row)">{{ $t("common.edit") }}</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="project-manage-pagination-div">
            <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
        </div>
        <el-button @click="clickAdd()" type="warning" class="project-manage-add-button" plain>
            <el-icon>
                <Plus />
            </el-icon>
            <span>{{ $t("common.add") }}</span>
        </el-button>
    </el-card>
    <el-drawer v-model="isDrawerOpen">
        <template #header>
            <span class="project-manage-drawer-title">{{ $t("static.editAndAddInterface") }}</span>
        </template>
        <el-form ref="projectFormRef" :model="projectForm" :rules="projectFormRules">
            <el-form-item prop="name">
                <h1>{{ $t("common.name") }}</h1>
                <el-input v-model="projectForm.name" maxlength="30" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item prop="description">
                <h1>{{ $t("common.description") }}</h1>
                <el-input v-model="projectForm.description" :rows="2" type="textarea" maxlength="50" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item prop="isEnded">
                <h1>{{ $t("common.state") }}</h1>
                <el-select v-model="projectForm.isEnded">
                    <el-option v-for="(item,i) in endedState" :key="i" :label="item.label" :value="item.value"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="clickAddOrEditProject(projectFormRef)" type="success">
                    <el-icon class="project-submit-button-icon" size="15">
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
import { getProjectManageList,addOrUpdateProject } from '@/axios/api/project';
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus' //element消息
import i18n from '@/language';
const { t } = i18n.global

const tableData = ref([]) //主页面表表格数据
const page = ref(1) //页数
const pageTotal = ref(0) //总条数
const tableHeight = ref((window.innerHeight - 290) + "px") //表格高度
const isDrawerOpen = ref(false) //抽屉是否打开
const projectForm = reactive //接口数据输入表单
({
    name: "",
    description: "",
    isEnded: "",
})
const projectFormRef = ref<FormInstance>() //添加 project 表单的 ref
const projectFormRules = reactive //添加 project 表单的 rule
({
    name:
    [
        { required: true, message: t("rules.name"), trigger: 'blur' },
    ],
    description:
    [
        { required: true, message: t("rules.description"), trigger: 'blur' },
    ],
})
const projectEditUuid = ref("") //修改的类别 uuid
const endedState = 
[
    {
        value: '0',
        label: t("common.open"),
    },
    {
        value: '1',
        label: t("common.close"),
    },
]

const getProjectData = async () => //获取接口列表
{
    try
    {
        const params =
        {
            page: page.value,
        }
        const resp = await getProjectManageList(params)
        pageTotal.value = resp.data.total
        tableData.value = resp.data.projectList
    } catch {}
}

onMounted( () =>
{
    getProjectData()
})

const pageChange = () => //翻页
{
    getProjectData()
}

const clickEdit = (row:any) => //点击编辑按钮
{
    projectForm.name = row.name
    projectForm.description = row.description
    projectForm.isEnded = row.isEnded
    isDrawerOpen.value = true
    projectEditUuid.value = row.uuid
}

const clickAdd = () => //点击新增按钮
{
    projectForm.name = ""
    projectForm.description = ""
    projectForm.isEnded = "0"
    isDrawerOpen.value = true
    projectEditUuid.value = ""
}

const doAddOrEditProject = async () =>
{
    try
    {
        const params =
        {
            uuid: projectEditUuid.value,
            name: projectForm.name,
            description: projectForm.description,
            isEnded: projectForm.isEnded,
        }
        const resp = await addOrUpdateProject(params)
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
        getProjectData()
        isDrawerOpen.value = false
    } catch {}
}

const clickAddOrEditProject = async (formEl: FormInstance | undefined) => //点击添加或修改 project 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doAddOrEditProject()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const onWindowSizeChanged = () => //窗口大小变动
{
    tableHeight.value = (window.innerHeight - 290) + "px"
}

window.addEventListener('resize',onWindowSizeChanged) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.project-manage-add-button
{
    width: 100%;
    margin-top: 20px;
}

.project-manage-pagination-div
{
    margin-top: 20px;
}

.project-manage-drawer-title
{
    font-weight: bold;
}

.project-submit-button-icon
{
    margin-right: 10px;
}
</style>