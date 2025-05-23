<template>
  <div class="app">
    <el-container>
      <el-header>
        <el-menu
        :default-active="defaultActiveMenu"
        :ellipsis="false"
        mode="horizontal"
        router
        >
          <el-menu-item @click="clickMenu">
            <el-icon :size="25">
              <Menu />
            </el-icon>
          </el-menu-item>
          <el-menu-item index="home">
            <el-icon size="100px">
              <Management />
            </el-icon>
            <span class="app-menu-title-span-big">SRIC</span>
            <span class="app-menu-title-span-small">PMS</span>
          </el-menu-item>
          <div class="flex-grow-10"></div>
          <div class="app-user-message-div">
            <div v-if="!isMenuClosed">
              <menu-setting-buttons></menu-setting-buttons>
            </div>
            <div v-if="isSign" class="app-user-message-avatar-div">
              <el-avatar :src="avatarUrl" @click="clickAvatar"></el-avatar>
            </div>
            <div v-if="!isSign">
              <el-button type="primary" class="app-user-message-sign-button" @click="clickSignIn" text>{{ $t("common.signin") }}</el-button>
              <el-button type="primary" class="app-user-message-sign-button" @click="clickSignUp" text>{{ $t("common.signup") }}</el-button>
            </div>
          </div>
        </el-menu>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>

    <el-drawer v-model="menuDrawer" size="300" direction="ltr">
      <el-menu
      :default-active="defaultActiveMenu"
      :ellipsis="false"
      router
      @select="selectMenu"
      >
        <el-scrollbar :height="menuScrollHeight">
          <template v-for="(item,i) in mainMenuData" :key="i" >
            <el-menu-item :index="item.name" v-if="item.level >= userStatusNow">
              <span class="app-menu-item-span">{{ $t("mainMenu." + item.name) }}</span>
            </el-menu-item>
          </template>
        </el-scrollbar>
      </el-menu>
      <div v-if="isMenuClosed">
        <el-divider></el-divider>
        <menu-setting-buttons></menu-setting-buttons>
      </div>
    </el-drawer>

    <el-drawer v-model="personalMenuDrawer" size="300">
      <template #header>
        <div class="app-user-message">
          <div class="app-user-message-avatar-div">
            <el-avatar :src="avatarUrl" @click="clickAvatar"></el-avatar>
            <p class="app-user-message-name">{{ username }}</p>
          </div>
          <div class="app-user-message-uid">
            <span class="app-user-message-uid-text">UID : {{ userUid }}</span>
            <el-tag effect="dark" type="info" :color="getStatusColorCode(userStatusNow)">PM {{ userStatusNow }}</el-tag>
          </div>
        </div>
      </template>
      <el-divider></el-divider>
      <el-button @click="clickUserProfile" class="app-user-message-menu-button" text>
        <el-icon class="app-user-message-menu-icon">
          <User />
        </el-icon>
        {{ $t("menu.userProfile") }}
      </el-button>
      <div v-if="isManagementShow">
        <el-divider></el-divider>
        <el-button @click="clickManagement" class="app-user-message-menu-button" text>
          <el-icon class="app-user-message-menu-icon">
            <SetUp />
          </el-icon>
          {{ $t("common.management") }}
        </el-button>
      </div>
      <el-divider></el-divider>
      <el-button @click="clickSignOut" class="app-user-message-menu-button" text>
        <el-icon class="app-user-message-menu-icon">
          <SwitchButton />
        </el-icon>
        {{ $t("sign.signout") }}
      </el-button>
    </el-drawer>

    <el-dialog v-model="signOutDialogVisible" width="500">
      <template #header>{{ $t("common.tips") }}</template>
      {{ $t("static.signOutConfirm") }}
      <template #footer>
        <el-button @click="signOutDialogVisible = false" text bg>
          {{ $t("common.cancel") }}
        </el-button>
        <el-button type="danger" @click="clickConfirmSignOut">
          {{ $t("common.confirm") }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { useRoute,useRouter } from 'vue-router'
import { ref,computed,onMounted,h } from 'vue'
import axios from 'axios';
import { getUserMessage } from '@/axios/api/user'
import { getPermissionMainMenu } from './axios/api/permissionMainMenu';
import { getStatusColorCode } from '@/tools/tool'
import { ElNotification } from 'element-plus'
import MenuSettingButtons from './components/MenuSettingButtons.vue';
import i18n from '@/language';
const { t } = i18n.global

let route = useRoute()
let router = useRouter()
const defaultActiveMenu = computed( () => { return route.name })//访问页面时默认菜单选项

const avatarUrl = ref(axios.defaults.baseURL + "/static/file/userAvatar/NULL.webp") //头像URL
const username = ref("NULL") //用户名显示
const userUid = ref("0") //用户UID
const isSign = ref(false) //是否已经登录
const isManagementShow = ref(false) //是否显示管理按钮
const menuDrawer = ref(false) //菜单抽屉状态
const personalMenuDrawer = ref(false) //个人菜单抽屉状态
const signOutDialogVisible = ref(false) //退出登录对话框状态
const isMenuClosed = ref(false) //菜单是否折叠
const menuScrollHeight = ref((window.innerHeight - 175) + "px") //菜单滑动高度
const mainMenuData:any = ref([]) //主菜单内容数据
const userStatusNow = ref(10) //当前用户权限等级

const loadMainMenuData = async () =>
{
  try
  {
    const resp = await getPermissionMainMenu({})
    mainMenuData.value = resp.data
  } catch {}
}

onMounted( () =>
{
  loadMainMenuData()
})

const clickMenu = () => //打开菜单
{
  menuDrawer.value = true
}

const selectMenu = () => //选择菜单 需要关闭菜单弹窗
{
  menuDrawer.value = false
}

const clickSignIn = () => //点击登录
{
  router.push("Signin")
}

const clickSignUp = () => //点击注册
{
  router.push("Signup")
}

const clickAvatar = () => //点击头像
{
  personalMenuDrawer.value = true
}

const clickUserProfile = () => //点击用户信息
{
  personalMenuDrawer.value = false
  router.push("UserProfile")
}

const clickManagement = () => //点击管理
{
  personalMenuDrawer.value = false
  router.push('Manage')
}

const clickSignOut = () => //点击登出
{
  signOutDialogVisible.value = true
}

const clickConfirmSignOut = () => //点击确认登出
{
  localStorage.setItem("isSignIn","false")
  localStorage.removeItem("token")
  localStorage.removeItem("uuid")
  localStorage.removeItem("uid")
  localStorage.removeItem("userStatus")
  isSign.value = false
  signOutDialogVisible.value = false
  personalMenuDrawer.value = false
  router.push("SignIn")
}

const checkUserMessage = async () => //更新用户信息(用户名，头像)
{
  try
  {
    const resp = await getUserMessage({ uuid: localStorage.getItem("uuid") })
    username.value = resp.data.username
    userUid.value = resp.data.uid
    avatarUrl.value = axios.defaults.baseURL + "/static/file/userAvatar/" + resp.data.avatar
    if(resp.data.signCode == 500) //token过期
    {
      ElNotification({
        title: t("common.noties"),
        message: h('i', { style: 'color: teal' }, t("sign.tokenTimeOut")),
        offset: 50,
      })
      localStorage.setItem("isSignIn","false")
      localStorage.removeItem("token")
      localStorage.removeItem("uuid")
      localStorage.removeItem("uid")
      localStorage.removeItem("userStatus")
      isSign.value = false
      router.push("Signin")
    }
  } catch {}
}

const checkSignLocalStorage = () => //检查是否登录的localStorage
{
  if(localStorage.getItem("isSignIn") == "true" || false) //已登录 (没有值即为false)
  {
    isSign.value = true
    userStatusNow.value = Number(localStorage.getItem("userStatus"))
    if(userStatusNow.value <= 3) //获取用户的权限等级进行判断 权限等级高于 3 级时显示管理按钮
    {
      isManagementShow.value = true
    }
    else
    {
      isManagementShow.value = false
    }
    checkUserMessage() //更新用户信息(用户名，头像)
  }
  else //未登录
  {
    isSign.value = false
    userStatusNow.value = 10 //重置用户默认权限为10
    isManagementShow.value = false
  }
}

router.beforeEach((to, from, next) => { //路由变动
  checkSignLocalStorage()//检查是否登录的localStorage 
  next()
})

const windowSizeChange = () => //窗口大小变化时检测
{
  if(window.innerWidth < 800) //在窗口过小时折叠菜单栏
  {
    isMenuClosed.value = true
  }
  else
  {
    isMenuClosed.value = false
  }
  menuScrollHeight.value = (window.innerHeight - 175) + "px"
}

checkSignLocalStorage() //初始化时检查一次登录状态
windowSizeChange() //初始化时检查窗口大小
 
window.addEventListener('resize',windowSizeChange) //监听窗口变动

</script>

<style>
@import '@/css/common.css';

.app .el-header  
{
  padding: 0;
}

.flex-grow-10
{
  flex-grow: 10;
}

.app-menu-title-span-big
{
  font-size: 30px;
  font-weight: 900;
  margin-right: 10px;
}

.app-menu-title-span-small
{
  font-size: 15px;
  font-weight: 900;
}

.app-menu-item-span
{
  font-size: 15px;
  font-weight: 900;
}

.app-user-message-div
{
  display: flex;
  align-items: center;
}

.app-user-message
{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.app-user-message-avatar-div
{
  display: flex;
  align-items: center;
  margin-left: 10px;
  margin-right: 10px;
}

.app-user-message-name
{
  margin-left: 20px;
  font-weight: bold;
  font-size: 20px;
  color: black;
}

html.dark .app-user-message-name
{
  color: white;
}

.app-user-message-uid
{
  display: flex;
  justify-content: space-between;
  margin-left: 70px;
  font-weight: bold;
  font-size: 12px;
  color: black;
}

html.dark .app-user-message-uid
{
  color: white;
}

.app-user-message-uid-text
{
  margin: 4px 10px 0px 0px;
}

.app-user-message-menu-button
{
  width: 100%;
}

.app-user-message-menu-icon
{
  margin-right: 10px;
}

.app-user-message-sign-button
{
  margin: 0px !important;
}

.app-user-message-setting-card
{
  position: fixed;
}

:deep(.el-radio-group)
{
  display: block;
}

:deep(.el-radio)
{
  display: block;
}

.el-drawer__header
{
  margin-bottom: 0px !important;
}
</style>