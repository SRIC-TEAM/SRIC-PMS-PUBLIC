<template>
    <el-card>
        <el-table :data="tableData" :height="tableHeight">
            <el-table-column prop="id">
                <template #header>
                    TAG ID
                </template>
            </el-table-column>
            <el-table-column prop="name">
                <template #header>
                    {{ $t("common.name") }}
                </template>
            </el-table-column>
            <el-table-column prop="number">
                <template #header>
                    {{ $t("static.itemNumber") }}
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
        <el-button @click="clickAdd()" type="warning" class="h-comic-tag-manage-add-button" plain>
            <el-icon>
                <Plus />
            </el-icon>
            <span>{{ $t("common.add") }}</span>
        </el-button>
    </el-card>
    <el-drawer v-model="isDrawerOpen">
        <template #header>
            <span class="h-comic-tag-manage-drawer-title">{{ $t("static.editAndAddTag") }}</span>
        </template>
        <el-form ref="tagFormRef" :model="tagForm" :rules="tagFormRules">
            <el-form-item prop="name">
                <h1>{{ $t("common.name") }}</h1>
                <el-input v-model="tagForm.name" maxlength="10" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="clickAddOrEditTag(tagFormRef)" type="success">
                    <el-icon class="h-comic-tag-manage-submit-button-icon" size="15">
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
import { getHComicTagManageDataList,addOrUpdateHComicTag } from '@/axios/api/hManage';
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus' //element消息
import i18n from '@/language';
const { t } = i18n.global

const tableData = ref([]) //主页面表表格数据
const tableHeight = ref((window.innerHeight - 240) + "px") //表格高度
const isDrawerOpen = ref(false) //抽屉是否打开
const tagForm = reactive //类别名称输入
({
    name: "",
})
const tagFormRef = ref<FormInstance>() //添加 h comic tag 表单的 ref
const tagFormRules = reactive //添加 h comic tag 表单的 rule
({
    name:
    [
        { required: true, message: t("rules.name"), trigger: 'blur' },
    ],
})
const tagEditId = ref(0) //修改的类别 id

const getHComicTagData = async () => //获取类别列表
{
    try
    {
        const resp = await getHComicTagManageDataList({})
        tableData.value = resp.data
    } catch {}
}

onMounted( () =>
{
    getHComicTagData()
})

const clickEdit = (row:any) => //点击编辑按钮
{
    tagForm.name = row.name
    isDrawerOpen.value = true
    tagEditId.value = row.id
}

const clickAdd = () => //点击新增按钮
{
    tagForm.name = ""
    isDrawerOpen.value = true
    tagEditId.value = 0
}

const doAddOrEditTag = async () =>
{
    try
    {
        const params =
        {
            id: tagEditId.value,
            name: tagForm.name,
        }
        const resp = await addOrUpdateHComicTag(params)
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
        getHComicTagData()
        isDrawerOpen.value = false
    } catch {}
}

const clickAddOrEditTag = async (formEl: FormInstance | undefined) => //点击添加或修改 h comic tag 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doAddOrEditTag()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const onWindowSizeChanged = () => //窗口大小变动
{
    tableHeight.value = (window.innerHeight - 240) + "px"
}

window.addEventListener('resize',onWindowSizeChanged) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.h-comic-tag-manage-add-button
{
    width: 100%;
    margin-top: 20px;
}

.h-comic-tag-manage-drawer-title
{
    font-weight: bold;
}

.h-comic-tag-manage-submit-button-icon
{
    margin-right: 10px;
}
</style>