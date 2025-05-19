<template>
    <div class="common-body-set-width">
        <el-container>
            <el-aside :width="asideWidth">
                <el-menu
                :style="minHeight"
                :default-active="deafultActiveMenu"
                :ellipsis="false"
                :collapse="isCollapse"
                router
                >
                    <el-sub-menu index="systemManage">
                        <template #title>
                            <el-icon><Setting /></el-icon>
                            <span class="manage-index-menu-item-span">{{ $t("menu.systemManage") }}</span>
                        </template>
                        <el-menu-item index="UserManage">
                            <el-icon :size="25"><User /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.user") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="PermissionRequestManage">
                            <el-icon :size="25"><Lock /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.permissionRequest") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="PermissionMainMenuManage">
                            <el-icon :size="25"><Lock /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.permissionMainMenu") }}</span>
                            </template>
                        </el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="hManage">
                        <template #title>
                            <el-icon><ToiletPaper /></el-icon>
                            <span class="manage-index-menu-item-span">{{ $t("menu.hManage") }}</span>
                        </template>
                        <el-menu-item index="HVideoManage">
                            <el-icon :size="25"><VideoPlay /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.video") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="HVideoTagManage">
                            <el-icon :size="25"><Discount /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.hVideoTag") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="HComicManage">
                            <el-icon :size="25"><Monitor /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.comic") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="HComicTagManage">
                            <el-icon :size="25"><Discount /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.hComicTag") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="HPictureUpload">
                            <el-icon :size="25"><Upload /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.hPictureUplaod") }}</span>
                            </template>
                        </el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="dataManage">
                        <template #title>
                            <el-icon><PieChart /></el-icon>
                            <span class="manage-index-menu-item-span">{{ $t("menu.dataManage") }}</span>
                        </template>
                        <el-menu-item index="LibraryManage">
                            <el-icon :size="25"><Collection /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.library") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="LibraryCategoryManage">
                            <el-icon :size="25"><Collection /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.libraryCategory") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="NotesManage">
                            <el-icon :size="25"><MessageBox /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.notes") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="ResourcesManage">
                            <el-icon :size="25"><FolderOpened /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.resources") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="ResourcesCategoryManage">
                            <el-icon :size="25"><FolderOpened /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.resourcesCategory") }}</span>
                            </template>
                        </el-menu-item>
                    </el-sub-menu>
                    <el-sub-menu index="teamManage">
                        <template #title>
                            <el-icon><Finished /></el-icon>
                            <span class="manage-index-menu-item-span">{{ $t("menu.teamManage") }}</span>
                        </template>
                        <el-menu-item index="ProjectManage">
                            <el-icon :size="25"><TakeawayBox /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.project") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="ProjectUserManage">
                            <el-icon :size="25"><TakeawayBox /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.projectUser") }}</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="ProjectItemManageIndex">
                            <el-icon :size="25"><TakeawayBox /></el-icon>
                            <template #title>
                                <span class="manage-index-menu-item-span">{{ $t("menu.ProjectItem") }}</span>
                            </template>
                        </el-menu-item>
                    </el-sub-menu>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
  </div>
</template>

<script lang="ts" setup>
import { useRoute } from 'vue-router'
import { ref,computed } from 'vue'

let route = useRoute()
const deafultActiveMenu = computed( () => { return route.name })//访问页面时默认菜单选项
let minHeight = ref("min-height:" + (window.innerHeight - 100) + "px;")//设置菜单栏高度
let isCollapse = ref(false)
let asideWidth = ref("200px")

const resetMinHeightAndMenu = () =>
{
  minHeight.value = "min-height:" + (window.innerHeight - 100) + "px;" //重置菜单栏高度(适应窗口大小)
  if(window.innerWidth < 800) //在窗口过小时折叠菜单栏
  {
      isCollapse.value = true
      asideWidth.value = "75px"
  }
  else
  {
      isCollapse.value = false
      asideWidth.value = "200px"
  }
}

resetMinHeightAndMenu()

window.addEventListener('resize',resetMinHeightAndMenu) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.manage-index-menu-item-span
{
  font-size: 20px;
  font-weight: bold;
}
</style>