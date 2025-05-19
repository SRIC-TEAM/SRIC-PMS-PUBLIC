<template>
    <div class="common-body-set-width">
        <div class="user-profile-edit-title-div">
            <el-button type="info" @click="clickBack" plain>
                <el-icon size="20">
                    <Back/>
                </el-icon>
                <p class="user-profile-edit-back-button-text">{{ $t("common.back") }}</p>
            </el-button>
            <h1 class="user-profile-edit-title-h1">{{ $t("static.userProfileEdit") }}</h1>
        </div>
        <div class="user-profile-edit-avatar-div">
            <el-card class="user-profile-edit-avatar-show-card">
                <el-image :src="avatarUrl"></el-image>
            </el-card>
            <el-upload
                action=""
                ref="uploadRef"
                accept=".jpg,.jpeg,.png,.webp"
                :auto-upload="true"
                :limit="1"
                :show-file-list="false"
                :on-exceed="handleExceed"
                :http-request="uploadFileAndOpenCropper"
                >
                <template #trigger>
                    <el-button class="user-profile-edit-avatar-button" type="warning" plain>
                        <el-icon class="user-profile-edit-avatar-button-icon" size="15"><Upload /></el-icon>
                        {{ $t("static.uploadNewAvatar") }}
                    </el-button>
                </template>

            </el-upload>
        </div>
        <el-divider></el-divider>
        <el-form ref="userProfileFormRef" :model="userProfileForm" :rules="userProfileFormRules">
            <el-form-item prop="username">
                <h1>{{ $t("user.username") }}</h1>
                <el-input v-model="userProfileForm.username" maxlength="30" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item prop="mark">
                <h1>{{ $t("user.mark") }}</h1>
                <el-input v-model="userProfileForm.mark" maxlength="100" type="textarea" :autosize="{ minRows: 3 }" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click="clickEditUserProfile(userProfileFormRef)">
                    <el-icon class="user-profile-edit-submit-button-icon" size="15"><CircleCheckFilled/></el-icon>
                    {{ $t("common.submit") }}
                </el-button>
            </el-form-item>
        </el-form>
    </div>
    <el-drawer v-model="isUploadAvatarDrawerOpen" direction="ttb">
        <template #header>
            <h1>{{ $t("common.upload") }}</h1>
        </template>
        <template #default>
            <div class="user-profile-edit-upload-div">
                <div class="user-profile-edit-upload-text">{{ $t("static.reallyToUploadAvatar") }}</div>
                <div class="user-profile-edit-upload-cropper">
                    <vueCropper
                    ref="cropperRef"
                    :img="option.img"
                    :outputSize="option.outputSize"
                    :outputType="option.outputType"
                    :autoCrop="option.autoCrop"
                    :fixed="option.fixed"
                    :fixedBox="option.fixedBox"
                    :canMoveBox="option.canMoveBox"
                    :autoCropWidth="option.autoCropWidth"
                    :autoCropHeight="option.autoCropHeight"
                    ></vueCropper>
                </div>
            </div>
        </template>
        <template #footer>
            <el-button type="info" plain @click="clickCancelUpload()">{{ $t("common.no") }}</el-button>
            <el-button type="danger" @click="doUploadAvatar()">{{ $t("common.submit") }}</el-button>
        </template>
    </el-drawer>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref,reactive,h } from 'vue'
import { getUserMessage,editUserProfile,uploadAvatar } from '@/axios/api/user'
import { useRouter } from 'vue-router'
import { ElNotification,ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { toBase64 } from '@/tools/tool'
import 'vue-cropper/dist/index.css'
import { VueCropper }  from "vue-cropper";
import { genFileId } from 'element-plus'
import type { UploadInstance, UploadProps, UploadRawFile } from 'element-plus'
import i18n from '@/language';
const router = useRouter()
const { t } = i18n.global

const avatarUrl = ref(axios.defaults.baseURL + "/static/file/userAvatar/NULL.webp") //头像URL
const userStatus = ref(10) //用户 权限
const isUploadAvatarDrawerOpen = ref(false) //上传新头像的抽屉是否打开
const uploadRef = ref<UploadInstance>() //upload 组件的 ref
const cropperRef = ref<InstanceType<typeof VueCropper> | null>(null);

const userProfileForm = reactive //数据输入
({
    username: "",
    mark: "",
})
const userProfileFormRef = ref<FormInstance>() //添加 userProfile 表单的 ref
const userProfileFormRules = reactive //添加 userProfile 表单的 rule
({
    username:
    [
        { required: true, message: t("rules.username"), trigger: 'blur' },
        { pattern: /^[a-zA-Z0-9_-]+$/, message: t("rules.usernameNeeded"), trigger: 'blur' },
    ],
    mark:[],
})
const option = reactive
({
    img: "", //需要剪切的图片 base64
    outputSize: 0.5, //输出质量
    outputType: "webp", //输出格式
    autoCrop: "", //生成截图框
    fixed: true, //是否开启截图框宽高固定比例
    fixedBox: true, //是否固定截图框大小
    canMoveBox: false, //截图框能否拖动
    autoCropWidth: 200, //生成截图框宽度
    autoCropHeight:200, //生成截图框高度
})

const loadUserMessage = async () => //获取用户信息(用户名，头像)
{
    try
    {
        const resp = await getUserMessage({ uuid: localStorage.getItem("uuid") })
        userProfileForm.username = resp.data.username
        userStatus.value = userStatus.value = Number(localStorage.getItem("userStatus"))
        avatarUrl.value = axios.defaults.baseURL + "/static/file/userAvatar/" + resp.data.avatar
        userProfileForm.mark = resp.data.mark
        if(resp.data.signCode == 500) //token过期
        {
            ElNotification
            ({
                title: t("common.noties"),
                message: h('i', { style: 'color: teal' }, t("sign.tokenTimeOut")),
                offset: 50,
            })
            localStorage.setItem("isSignIn","false")
            localStorage.removeItem("token")
            localStorage.removeItem("uuid")
            localStorage.removeItem("uid")
        }
    } catch {}
}

const clickBack = () => //点击返回
{
    router.push
    ({
        name: 'UserProfile',
        path: '/UserProfile',
    })
}

const handleExceed: UploadProps['onExceed'] = (files) => //在上传新文件时 (仅允许上传一个文件 触发超出限制时，执行的钩子函数) 替换文件
{
    uploadRef.value?.clearFiles()
    const file = files[0] as UploadRawFile
    file.uid = genFileId()
    uploadRef.value?.handleStart(file)
    uploadRef.value?.submit() //替换文件后重新提交
}

const uploadFileAndOpenCropper = async (options) => //上传图片
{
    const file = options.file;

    // 检查文件类型
    const allowedTypes = ['image/jpeg', 'image/png', 'image/webp'];
    if (!allowedTypes.includes(file.type))
    {
        ElMessage
        ({
            message: t("static.uploadNeedBeImage"),
            type: 'warning',
        })
        return
    }

    // 检查文件大小是否大于 10MB
    const maxSizeMB = 10; // 文件大小限制为 10MB
    const maxSizeBytes = maxSizeMB * 1024 * 1024;
    if (file.size > maxSizeBytes)
    {
        ElMessage
        ({
            message: t("static.uploadFileTooLarge") + " < " + maxSizeMB + " MB",
            type: 'warning',
        });
        return;
    }

    try
    {
        const base64 = await toBase64(file);

        // 打开裁剪器并设置图片
        option.img = base64;
        isUploadAvatarDrawerOpen.value = true;
    }
    catch (error)
    {
        ElMessage
        ({
            message: t("static.uploadFailed"),
            type: 'error',
        });
    }
}

const clickCancelUpload = () => //点击取消上传头像
{
    isUploadAvatarDrawerOpen.value = false
}

const doUploadAvatar = () => //点击提交上传头像
{
    cropperRef.value.getCropBlob(data => 
    {
        const file = new File([data], 'avatar.webp', { type: 'image/webp' })
        doUploadAvatarRequest(file)
    })
}

const doUploadAvatarRequest = async (file:File) => //接收到File对象后 发送请求进行上传
{
    try
    {
        const resp = await uploadAvatar({},file)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.uploadSuccess"),
                type: 'success',
            })
            ElNotification
            ({
                title: t("common.noties"),
                message: h('i', { style: 'color: #67C23A' }, t("static.reloadAvatar")),
                offset: 50,
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
        isUploadAvatarDrawerOpen.value = false
    } catch {}
}

const clickEditUserProfile = async (formEl: FormInstance | undefined) => //点击提交编辑用户信息
{
    if (!formEl) return //表单验证
    await formEl.validate((valid, fields) => {
        if (valid) {
            doEditUserProfile()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const doEditUserProfile = async () => //发送编辑用户信息请求
{
    try
    {
        const params = 
        {
            uuid: localStorage.getItem("uuid"),
            username: userProfileForm.username,
            mark: userProfileForm.mark,
        }
        const resp = await editUserProfile(params)
        if(resp.data.code == 200) //修改成功
        {
            ElMessage({
                message: t("static.editSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400) //用户名已被注册
        {
            ElMessage({
                message: t("sign.usernameHasBeenUsedMessage"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 401) //错误
        {
            ElMessage({
                message: t("static.editFailed"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 403) //修改用户与当前用户不同
        {
            ElMessage({
                message: t("static.editFailedBecauseDifferentUser"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 500) //用户名不合法
        {
            ElMessage({
                message: t("sign.usernameInvalid"),
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

loadUserMessage()
</script>

<style>
@import '@/css/common.css';

.user-profile-edit-title-div
{
    display: flex;
    align-items: center; /* 垂直居中 */
    margin-bottom: 30px;
}

.user-profile-edit-back-button-text
{
    margin-left: 5px;
}

.user-profile-edit-title-h1
{
    margin: 0px 0px 0px 20px;
}

.user-profile-edit-avatar-div
{
    display: flex;
    flex-direction: row;
    align-items: center;
}

.user-profile-edit-avatar-show-card
{
    width: 100px;
    height: 100px;
}

.user-profile-edit-avatar-show-card .el-card__body
{
    padding: 0;
}

.user-profile-edit-avatar-button
{
    margin-left: 20px;
}

.user-profile-edit-avatar-button-icon
{
    margin-right: 10px;
}

.user-profile-edit-submit-button-icon
{
    margin-right: 10px;
}

.user-profile-edit-upload-div
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

.user-profile-edit-upload-text
{
    font-weight: bold;
    font-size: 30px;
}

.user-profile-edit-upload-cropper
{
    height: 200px;
    width: 200px;
    margin-top: 20px;
}
</style>
