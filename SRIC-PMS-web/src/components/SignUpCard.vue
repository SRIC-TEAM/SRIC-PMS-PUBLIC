<template>
    <el-card>
        <el-form ref="signUpFormRef" :model="signUpForm" :rules="signUpFormRules">
            <el-form-item prop="username">
                <template #label>
                    {{ $t("sign.username") }}
                </template>
                <el-input v-model="signUpForm.username" clearable></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <template #label>
                    {{ $t("sign.password") }}
                </template>
                <el-input v-model="signUpForm.password" type="password" show-password clearable></el-input>
            </el-form-item>
            <el-form-item prop="passwordRepeat">
                <template #label>
                    {{ $t("sign.repeatPassword") }}
                </template>
                <el-input v-model="signUpForm.passwordRepeat" type="password" show-password clearable></el-input>
            </el-form-item>
            <el-form-item prop="mail">
                <template #label>
                    {{ $t("user.mail") }}
                </template>
                <el-input v-model="signUpForm.mail" clearable></el-input>
            </el-form-item>
            <el-form-item prop="code">
                <template #label>
                    {{ $t("sign.code") }}
                </template>
                <el-input v-model="signUpForm.code" clearable>
                    <template #append>
                        <el-button v-if="!isDisabled" @click="clickSendCode" :loading="isSendCodeLoading">{{ $t("sign.sendCode") }}</el-button>
                        <el-button v-if="isDisabled" disabled>{{ timeCounting }} S</el-button>
                    </template>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="clickSignUp(signUpFormRef)" type="success" style="width: 100%;">{{ $t("common.signup") }}</el-button>
            </el-form-item>
        </el-form>
        <el-divider></el-divider>
        <div>
            <span class="SignInCard-new-alarm-span">{{ $t("static.alreadyHave") }}</span>
            <el-button @click="clickSignIn" class="SignInCard-more-button" type="primary" link>{{ $t("common.signin") }}</el-button>
        </div>
    </el-card>
</template>

<script lang="ts" setup>
import { ref,reactive } from 'vue'
import { getCodeByMail,signUp } from '@/axios/api/user';
import { ElMessage } from 'element-plus'
import CryptoJS from 'crypto-js' //SHA-256加密
import { useRouter } from "vue-router";
import type { FormInstance } from 'element-plus'
import i18n from '@/language';

const { t } = i18n.global

const router = useRouter()

const isSendCodeLoading = ref(false) //发送验证码按钮是否正在加载
const isDisabled = ref(false) //发送验证码按钮是否为倒计时
const timeCounting = ref("60") //发送验证码倒计时初始值

const signUpForm = reactive //登录信息表单
({
    username: '',
    password: '',
    passwordRepeat: '',
    mail: '',
    code: '',
})

const signUpFormRef = ref<FormInstance>() //注册信息表单的ref

const signUpFormRules = reactive //注册信息表单的rule
({
    username:
    [
        { required: true, message: t("rules.username"), trigger: 'blur' },
    ],
    password:
    [
        { required: true, message: t("rules.password"), trigger: 'blur' },
    ],
    passwordRepeat:
    [
        { required: true, message: t("rules.passwordRepeat"), trigger: 'blur' },
    ],
    mail:
    [
        { required: true, message: t("rules.mail"), trigger: 'blur' },
		{ type: 'email', message: t("rules.mailConfirm"), trigger: 'blur' },
    ],
    code:
    [
        { required: true, message: t("rules.code"), trigger: 'blur' },
    ],
})

const doSendCodeRequest = async () => //发送验证码请求
{
    try
    {
        const resp = await getCodeByMail({ mail: signUpForm.mail })
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("sign.sendSuccess"),
                type: 'success',
            })
            setMailTimeout()
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("sign.mailHasBeenUsed"),
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
        isSendCodeLoading.value = false
    } catch {}
}

const setMailTimeout = () => //设置按钮冷却时间
{
    let timeLeft = 60
    isDisabled.value = true
    timeCounting.value = `${timeLeft}`

    const timer = setInterval(() => 
    {
        timeLeft--
        timeCounting.value = `${timeLeft}`
        if(timeLeft <= 0)
        {
            clearInterval(timer)
            isDisabled.value = false
            timeCounting.value = "60"
        }
    }, 1000)
}

const clickSendCode = () => //点击发送验证码
{
    const mailCheck = /^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\.[a-zA-Z]{2,})$/
    if(mailCheck.test(signUpForm.mail)) //邮箱合法
    {
        doSendCodeRequest()
        isSendCodeLoading.value = true
    }
    else
    {
        ElMessage({
            message: t("rules.mailConfirm"),
            type: 'warning',
        })
    }
}

const doSignUpRequest = async () => //发送注册请求
{
    try
    {
        let passwordHash = CryptoJS.HmacSHA256(signUpForm.password,"SRIC") //使用SHA-256进行哈希运算
        let passwordHashString = CryptoJS.enc.Hex.stringify(passwordHash) //将哈希运算的结果进行16进制编码
        const params =
        {
            username: signUpForm.username,
            password: passwordHashString,
            mail: signUpForm.mail,
            code: signUpForm.code,
        }
        const resp = await signUp(params)
        if(resp.data.code == 200) //注册成功
        {
            ElMessage({
                message: t("sign.successSignUpMessage"),
                type: 'success',
            })
            router.push("SignIn")
        }
        else if(resp.data.code == 400) //用户名已被注册
        {
            ElMessage({
                message: t("sign.usernameHasBeenUsedMessage"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 401) //验证码错误
        {
            ElMessage({
                message: t("sign.wrongCodeMessage"),
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

const clickSignUp = async (formEl: FormInstance | undefined) => //点击注册
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            if(signUpForm.password == signUpForm.passwordRepeat)
            {
                doSignUpRequest()
            }
            else
            {
                ElMessage({
                    message: t("sign.passwordCommon"),
                    type: 'warning',
                })
            }
        } else {
            console.log('error submit!', fields)
        }
    })
}

const clickSignIn = () => //点击跳转到登录
{
    router.push("SignIn")
}
</script>

<style>
.SignInCard-new-alarm-span
{
    font-size: 14px;
}

.SignInCard-more-button
{
    float: right;
}
</style>