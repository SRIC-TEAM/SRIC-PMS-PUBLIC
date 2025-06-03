<template>
    <div class="common-body-set-width">
        <div class="user-profile-body-div">
            <el-avatar :src="avatarUrl" :size="100" @click="clickEditUserProfile"></el-avatar>
            <div class="user-profile-details-div">
                <div>
                    <span class="user-profile-details-username" @click="copyText(username)">{{ username }}</span>
                    <el-icon size="25" class="user-profile-details-username-edit-icon" @click="clickEditUserProfile"><Edit /></el-icon>
                </div>
                <div>
                    <span class="user-profile-details-uid" @click="copyText(userUid)">UID : {{ userUid }}</span>
                    <el-tag effect="dark" type="info" :color="getStatusColorCode(userStatus)" class="user-profile-details-status">PM {{ userStatus }}</el-tag>
                </div>
            </div>
        </div>
        <div class="user-profile-details-mark">
            <span>{{ mark }}</span>
        </div>
        <el-divider></el-divider>
    </div>
</template>

<script lang="ts" setup>
import axios from 'axios';
import { ref } from 'vue'
import { getUserMessage } from '@/axios/api/user'
import { getStatusColorCode,copyText } from '@/tools/tool'
import { useRouter } from 'vue-router'
import { ElNotification } from 'element-plus'
import { h } from 'vue'
import i18n from '@/language';
const router = useRouter()
const { t } = i18n.global

const avatarUrl = ref(axios.defaults.baseURL + "/static/file/userAvatar/NULL.webp") //头像URL
const username = ref("NULL") //用户名显示
const userUid = ref("0") //用户 UID
const userStatus = ref(10) //用户 权限
const mark = ref("NULL MARK") //

const loadUserMessage = async () => //获取用户信息(用户名，头像)
{
    try
    {
        const resp = await getUserMessage({ uuid: localStorage.getItem("uuid") })
        username.value = resp.data.username
        userUid.value = resp.data.uid
        userStatus.value = userStatus.value = Number(localStorage.getItem("userStatus"))
        avatarUrl.value = axios.defaults.baseURL + "/static/file/userAvatar/" + resp.data.avatar
        mark.value = resp.data.mark
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

const clickEditUserProfile = () =>
{
    router.push
    ({
        name: 'UserProfileEdit',
        path: '/UserProfileEdit',
    })
}

loadUserMessage()
</script>

<style>
@import '@/css/common.css';

.user-profile-body-div
{
    display: flex;
    align-items: flex-end;
    margin-left: 20px;
}

.user-profile-details-div
{
    display: flex;
    flex-direction: column;
    margin-left: 20px; /* 调整此值以控制文本与头像之间的距离 */
}

.user-profile-details-username
{
    font-weight: bold;
    font-size: 30px;
}

.user-profile-details-username-edit-icon
{
    margin-left: 20px;
}

.user-profile-details-uid
{
    font-weight: bold;
}

.user-profile-details-mark
{
    margin-top: 20px;
    margin-left: 10px;
}

.user-profile-details-status
{
    margin: 0px 0px 0px 10px;
}
</style>
