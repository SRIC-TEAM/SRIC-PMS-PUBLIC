<template>
    <div class="common-body-set-width">
        <el-card>
            <div class="picture-upload-title-div">
                <el-icon class="picture-upload-title-icon" size="25"><upload-filled /></el-icon>
                <span class="picture-upload-title-span">{{ $t("static.uploadPicture") }}</span>
            </div>
            <div class="picture-number-display">
                {{ $t("static.pictureNumber") }}：<span class="number-highlight">{{ num }}</span>
            </div>
            <el-upload
                class="resources-upload-el-upload"
                ref="uploadRef"
                v-model:file-list="fileList"
                drag
                action=""
                accept=".webp,.gif"
                list-type="picture"
                :auto-upload="false"
                :limit="1"
                :on-exceed="handleExceed"
                :before-upload="beforeUpload"
                :http-request="uploadFileToServer"
            >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                {{ $t("static.dropFileHere") }} {{ $t("common.or") }} <em>{{ $t("static.clickToUpload") }}</em>
                </div>
                <template #tip>
                <div class="picture-upload-tip">
                    {{ $t("static.newFileWillCover") }}
                </div>
                </template>
            </el-upload>
            <el-button @click="clickUploadFile()" type="success" :loading="isUploadButtonLoading">
                <el-icon class="picture-upload-submit-button-icon" size="15">
                    <CircleCheckFilled/>
                </el-icon>
                {{ $t("common.submit") }}
            </el-button>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue'

import { getHPictureNumber, uploadPicture } from "@/axios/api/hManage"
import { genFileId } from 'element-plus'
import type { UploadInstance, UploadProps, UploadRawFile, UploadUserFile } from 'element-plus'
import { ElMessage } from 'element-plus' //element消息
import i18n from '@/language';
const { t } = i18n.global

const isUploadButtonLoading = ref(false) //上传按钮是否在加载中

const uploadRef = ref<UploadInstance>() //upload 组件的 ref

const fileList = ref<UploadUserFile[]>([]); //储存每次上传的文件对象和数据 (目前仅用于鉴定上传是否为空)

const num = ref(0) //图片编号

onMounted( () =>
{
    getHPictureNumberData()
})

const getHPictureNumberData = async () => //获取类别列表
{
    try
    {
        const resp = await getHPictureNumber({})
        num.value = resp.data
    } catch {}
}

const handleExceed: UploadProps['onExceed'] = (files) => //在上传新文件时 (仅允许上传一个文件 触发超出限制时，执行的钩子函数) 替换文件
{
    uploadRef.value?.clearFiles()
    const file = files[0] as UploadRawFile
    file.uid = genFileId()
    uploadRef.value?.handleStart(file)
}

const beforeUpload: UploadProps['beforeUpload'] = (rawFile) => //在上传文件前 判断文件类型 必须时 webp 文件或者 gif 动图文件
{
    const isValidType = ['image/webp', 'image/gif'].includes(rawFile.type)
    if (!isValidType) {
        ElMessage.error(t("static.pictureMustBe"))
        return false
    }
    return true
}

const clickUploadFile = async () => //点击上传 判断上传文件不为空,表单验证通过 触发上传请求
{
    if (!fileList.value.length) //判断文件是否为空
    {
        ElMessage({
            message: t("static.pleaseUploadFile"),
            type: 'warning',
        })
        return;
    }
    uploadRef.value?.submit()
}

const uploadFileToServer = async (options) => //上传请求
{
    isUploadButtonLoading.value = true
    try
    {
        const resp = await uploadPicture({},options.file)
        if(resp.data.code == 200)
        {
            isUploadButtonLoading.value = false
            uploadRef.value?.clearFiles()
            getHPictureNumberData()
            ElMessage({
                message: t("static.uploadSuccess"),
                type: 'success',
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

.picture-upload-title-div
{
    display: flex;
    align-items: center; /* 垂直居中 */
    margin-bottom: 10px;
}

.picture-upload-title-icon
{
    margin-right: 10px;
}

.picture-upload-title-span
{
    font-size: 20px;
    font-weight: bold;
    margin-right: 10px; /* 调整间距 */ 
}

.picture-upload-tip
{
    margin-top: 10px;
    font-size: 12px;
}

.picture-upload-submit-button-icon
{
    margin-right: 10px;
}

.picture-number-display {
    margin: 10px 0;
    font-size: 16px;
}

.number-highlight {
    font-size: 24px;
    font-weight: bold;
    color: #409eff;
}
</style>