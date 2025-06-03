<template>
    <div class="common-body-set-width">
        <el-card>
            <div class="resources-upload-title-div">
                <el-button type="info" @click="clickBack" plain>
                    <el-icon size="20">
                        <Back/>
                    </el-icon>
                    <p class="resources-upload-back-button-text">{{ $t("common.back") }}</p>
                </el-button>
                <h1 class="resources-upload-title-h1">{{ $t("static.uploadResourcesFile") }}</h1>
            </div>
            <el-form ref="resourcesUploadFormRef" :model="resourcesUploadForm" :rules="resourcesUploadFormRules">
                <el-form-item prop="name">
                    <h1>{{ $t("common.name") }}</h1>
                    <el-input v-model="resourcesUploadForm.name" maxlength="30" show-word-limit clearable></el-input>
                </el-form-item>
                <el-form-item prop="level">
                    <h1>{{ $t("static.permissionLevel") }}</h1>
                    <el-select v-model="resourcesUploadForm.level">
                        <el-option v-for="(item,i) in levels" :key="i" :label="item.label" :value="item.value">
                            <el-tag :color="item.label" class="resources-upload-select-color-tag" size="small" />
                            <span :style="{ color: item.label }">{{ $t("static.permissionLevel") }} : {{ item.value }}</span>
                        </el-option>
                        <template #label="{ value }">
                            <span>{{ $t("static.permissionLevel") }} : {{ value }}</span>
                        </template>
                    </el-select>
                </el-form-item>
                <el-form-item prop="category">
                    <h1>{{ $t("common.category") }}</h1>
                    <el-select v-model="resourcesUploadForm.category">
                        <el-option v-for="(item,i) in resourcesCategoryList" :key="i" :label="item.name" :value="item.id">
                            <span :style="{ color: item.name }">{{ item.name }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <el-upload
                class="resources-upload-el-upload"
                ref="uploadRef"
                v-model:file-list="fileList"
                drag
                action=""
                :auto-upload="false"
                :limit="1"
                :on-exceed="handleExceed"
                :http-request="uploadFileToServer"
            >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                {{ $t("static.dropFileHere") }} {{ $t("common.or") }} <em>{{ $t("static.clickToUpload") }}</em>
                </div>
                <template #tip>
                <div class="resources-upload-tip">
                    {{ $t("static.newFileWillCover") }}
                </div>
                </template>
            </el-upload>
            <el-button @click="clickUploadFile(resourcesUploadFormRef)" type="success" :loading="isUploadButtonLoading">
                <el-icon class="resources-upload-submit-button-icon" size="15">
                    <CircleCheckFilled/>
                </el-icon>
                {{ $t("common.submit") }}
            </el-button>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { ref,reactive,onMounted } from 'vue'
import { useRouter } from 'vue-router'

import { uploadResources,checkResourcesName,getResourcesCategoryManageDataList } from "@/axios/api/resources"
import { levels } from '@/tools/staticResources' //引入权限静态数据
import { genFileId } from 'element-plus'
import type { UploadInstance, UploadProps, UploadRawFile, UploadUserFile } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus' //element消息
import i18n from '@/language';
const { t } = i18n.global

const router = useRouter()

const isUploadButtonLoading = ref(false) //上传按钮是否在加载中

const uploadRef = ref<UploadInstance>() //upload 组件的 ref

const fileList = ref<UploadUserFile[]>([]); //储存每次上传的文件对象和数据 (目前仅用于鉴定上传是否为空)

const resourcesUploadForm = reactive //数据输入
({
    name: "",
    level: "",
    category: "",
})
const resourcesUploadFormRef = ref<FormInstance>() //添加 resourcesUpload 表单的 ref
const resourcesUploadFormRules = reactive //添加 resourcesUpload 表单的 rule
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
const resourcesCategoryList = ref<{ id: string; name: string }[]>([]) //资源类别列表

const clickBack = () =>
{
    router.push
    ({
        name: 'ResourcesManage',
        path: '/ResourcesManage',
    })
}

const getResourcesCategoryData = async () => //获取类别列表
{
    try
    {
        const resp = await getResourcesCategoryManageDataList({})
        resourcesCategoryList.value = resp.data
    } catch {}
}

onMounted( () =>
{
    getResourcesCategoryData()
})

const handleExceed: UploadProps['onExceed'] = (files) => //在上传新文件时 (仅允许上传一个文件 触发超出限制时，执行的钩子函数) 替换文件
{
    uploadRef.value?.clearFiles()
    const file = files[0] as UploadRawFile
    file.uid = genFileId()
    uploadRef.value?.handleStart(file)
}

const clickUploadFile = async (formEl: FormInstance | undefined) => //点击上传 判断上传文件不为空,表单验证通过 触发上传请求
{
    if (!fileList.value.length) //判断文件是否为空
    {
        ElMessage({
            message: t("static.pleaseUploadFile"),
            type: 'warning',
        })
        return;
    }
    try //判断文件名称是否重复
    {
        const params =
        {
            name: resourcesUploadForm.name,
        }
        const resp = await checkResourcesName(params)
        if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.nameHasBeenUsed"),
                type: 'warning',
            })
            return
        }
    } catch {}
    if (!formEl) return //表单验证
    await formEl.validate((valid, fields) => {
        if (valid) {
            uploadRef.value?.submit()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const uploadFileToServer = async (options) => //上传请求
{
    isUploadButtonLoading.value = true
    try
    {
        const params =
        {
            name: resourcesUploadForm.name,
            level: resourcesUploadForm.level,
            category: resourcesUploadForm.category,
        }
        const resp = await uploadResources(params,options.file)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.uploadSuccess"),
                type: 'success',
            })
            router.push
            ({
                name: 'ResourcesManage',
                path: '/ResourcesManage',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.uploadFailed"),
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

</script>

<style>
@import '@/css/common.css';

.resources-upload-title-div
{
    display: flex;
    align-items: center; /* 垂直居中 */
}

.resources-upload-title-h1
{
    margin: 0px 0px 0px 20px;
}

.resources-upload-back-button-text
{
    margin-left: 5px;
}

.resources-upload-tip
{
    margin-top: 10px;
    font-size: 12px;
}

.resources-upload-select-color-tag
{
    margin-right: 8px;
    border: none;
    aspect-ratio: 1;
}

.resources-upload-submit-button-icon
{
    margin-right: 10px;
}

.resources-upload-el-upload
{
    margin-bottom: 10px;
}
</style>