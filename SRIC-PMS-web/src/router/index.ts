import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'main',
    redirect: '/home',
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('@/views/HomePage.vue'),
  },
  {
    path: '/Hfiles',
    name: 'Hfiles',
    redirect: '/HfilesVideo',
    component: () => import('@/views/H-files/H-files-index.vue'),
    children:
    [
      {
        path: '/HfilesVideo',
        name: 'HfilesVideo',
        component: () => import('@/views/H-files/HfilesVideo.vue'),
      },
      {
        path: '/HfilesComic',
        name: 'HfilesComic',
        component: () => import('@/views/H-files/HfilesComic.vue'),
      },
      {
        path: '/HfilesPicture',
        name: 'HfilesPicture',
        component: () => import('@/views/H-files/HfilesPicture.vue'),
      },
    ]
  },
  {
    path: '/HfilesVideoPlayer',
    name: 'HfilesVideoPlayer',
    component: () => import('@/views/H-files/HfilesVideoPlayer.vue'),
  },
  {
    path: '/HfilesVideoManagePlayer',
    name: 'HfilesVideoManagePlayer',
    component: () => import('@/views/H-files/HfilesVideoManagePlayer.vue'),
  },
  {
    path: '/HfilesComicReader',
    name: 'HfilesComicReader',
    component: () => import('@/views/H-files/HfilesComicReader.vue'),
  },
  {
    path: '/HfilesComicManageReader',
    name: 'HfilesComicManageReader',
    component: () => import('@/views/H-files/HfilesComicManageReader.vue'),
  },
  {
    path: '/Library',
    name: 'Library',
    component: () => import('@/views/library/LibraryIndex.vue'),
  },
  {
    path: '/LibraryReader',
    name: 'LibraryReader',
    component: () => import('@/views/library/LibraryReader.vue'),
  },
  {
    path: '/Notes',
    name: 'Notes',
    component: () => import('@/views/notes/NotesIndex.vue'),
  },
  {
    path: '/Resources',
    name: 'Resources',
    component: () => import('@/views/resources/ResourcesIndex.vue'),
  },
  {
    path: '/ResourcesUpload',
    name: 'ResourcesUpload',
    component: () => import('@/views/manage/ResourcesUpload.vue'),
  },
  {
    path: '/Project',
    name: 'Project',
    component: () => import('@/views/project/ProjectIndex.vue'),
  },
  {
    path: '/ProjectMessage',
    name: 'ProjectMessage',
    component: () => import('@/views/project/ProjectMessage.vue'),
  },
  {
    path: '/ProjectItemManage',
    name: 'ProjectItemManage',
    component: () => import('@/views/manage/ProjectItemManage.vue'),
  },
  {
    path: '/SignIn',
    name: 'SignIn',
    component: () => import('@/views/user/SignIn.vue'),
  },
  {
    path: '/SignUp',
    name: 'SignUp',
    component: () => import('@/views/user/SignUp.vue'),
  },
  {
    path: '/UserProfile',
    name: 'UserProfile',
    component: () => import('@/views/user/UserProfile.vue'),
  },
  {
    path: '/UserProfileEdit',
    name: 'UserProfileEdit',
    component: () => import('@/views/user/UserProfileEdit.vue'),
  },
  {
    path: '/Manage',
    name: 'Manage',
    redirect: '/Usermanage',
    component: () => import('@/views/manage/ManageIndex.vue'),
    children:
    [
      {
        path: '/UserManage',
        name: 'UserManage',
        component: () => import('@/views/manage/UserManage.vue'),
      },
      {
        path: '/LibraryManage',
        name: 'LibraryManage',
        component: () => import('@/views/manage/LibraryManageIndex.vue'),
      },
      {
        path: '/LibraryManageReader',
        name: 'LibraryManageReader',
        component: () => import('@/views/manage/LibraryManageReader.vue'),
      },
      {
        path: '/LibraryCategoryManage',
        name: 'LibraryCategoryManage',
        component: () => import('@/views/manage/LibraryCategoryManage.vue'),
      },
      {
        path: '/PermissionRequestManage',
        name: 'PermissionRequestManage',
        component: () => import('@/views/manage/PermissionRequestManage.vue'),
      },
      {
        path: '/PermissionMainMenuManage',
        name: 'PermissionMainMenuManage',
        component: () => import('@/views/manage/PermissionMainMenuManage.vue')
      },
      {
        path: '/HVideoManage',
        name: 'HVideoManage',
        component: () => import('@/views/manage/HVideoManage.vue')
      },
      {
        path: '/HVideoTagManage',
        name: 'HVideoTagManage',
        component: () => import('@/views/manage/HVideoTagManage.vue')
      },
      {
        path: '/HComicManage',
        name: 'HComicManage',
        component: () => import('@/views/manage/HComicManage.vue')
      },
      {
        path: '/HComicTagManage',
        name: 'HComicTagManage',
        component: () => import('@/views/manage/HComicTagManage.vue')
      },
      {
        path: '/HPictureUpload',
        name: 'HPictureUpload',
        component: () => import('@/views/manage/HPictureUpload.vue')
      },
      {
        path: '/NotesManage',
        name: 'NotesManage',
        component: () => import('@/views/manage/NotesManage.vue')
      },
      {
        path: '/ResourcesManage',
        name: 'ResourcesManage',
        component: () => import('@/views/manage/ResourcesManage.vue'),
      },
      {
        path: '/ResourcesCategoryManage',
        name: 'ResourcesCategoryManage',
        component: () => import('@/views/manage/ResourcesCategoryManage.vue'),
      },
      {
        path: '/ProjectManage',
        name: 'ProjectManage',
        component: () => import('@/views/manage/ProjectManage.vue'),
      },
      {
        path: '/ProjectUserManage',
        name: 'ProjectUserManage',
        component: () => import('@/views/manage/ProjectUserManage.vue'),
      },
      {
        path: '/ProjectItemManageIndex',
        name: 'ProjectItemManageIndex',
        component: () => import('@/views/manage/ProjectItemManageIndex.vue'),
      },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
