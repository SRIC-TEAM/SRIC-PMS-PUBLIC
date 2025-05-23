<template>
    <div class="common-body-set-width">
        <div class="notes-search-div">
            <el-row>
                <el-col :span="20">
                    <el-input
                    v-model="searchInput"
                    placeholder="search"
                    clearable
                    >
                    <template #append>
                        <el-button @click="clickSearch">
                            <el-icon>
                                <search/>
                            </el-icon>
                        </el-button>
                    </template>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <el-button @click="clickAddNote" class="notes-add-button" type="danger">
                        <el-icon size="15">
                            <Plus/>
                        </el-icon>
                    </el-button>
                </el-col>
            </el-row>
        </div>
        <el-card class="notes-timeline">
            <el-scrollbar :height="scrollbarHeight" ref="notesScrollBarRef">
                <el-timeline>
                    <el-timeline-item v-if="isNewNoteShow" center placement="top" :timestamp="newNoteTimeShow">
                        <el-card>
                            <el-form ref="notesFormRef" :model="notesForm" :rules="notesFormRules" label-width="auto">
                                <el-form-item prop="title">
                                    <template #label>
                                        {{ $t("common.title") }}
                                    </template>
                                    <el-input v-model="notesForm.title" clearable show-word-limit maxlength="50"></el-input>
                                </el-form-item>
                                <el-divider></el-divider>
                                <el-form-item prop="text">
                                    <template #label>
                                        {{ $t("common.content") }}
                                    </template>
                                    <el-input v-model="notesForm.text" clearable show-word-limit maxlength="2500" type="textarea" :autosize="{ minRows: 3 }"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="success" plain @click="clickAddNoteConfirm(notesFormRef)">
                                        <el-icon><Check /></el-icon>
                                    </el-button>
                                    <el-button type="info" plain @click="clickCancelAdd()">
                                        <el-icon><Close /></el-icon>
                                    </el-button>
                                </el-form-item>
                            </el-form>
                        </el-card>
                    </el-timeline-item>
                    <el-timeline-item v-for="(item,i) in notesList" :key="i" center placement="top" :timestamp="item.createTime">
                        <el-card>
                            <span class="notes-card-title">{{ item.title }}</span>
                            <div class="notes-card-buttons-div">
                                <el-button class="notes-card-button" text circle @click="clickEdit(item)">
                                    <el-icon><Edit/></el-icon>
                                </el-button>
                                <el-popconfirm :title="deleteConfirmTitle" icon-color="#F56C6C" @confirm="confirmDelete(item)">
                                    <template #reference>
                                        <el-button class="notes-card-button" text circle @click="clickDelete()">
                                            <el-icon><Delete/></el-icon>
                                        </el-button>
                                    </template>
                                    <template #actions="{ confirm, cancel }">
                                        <el-button size="small" @click="cancel">{{ $t("common.no") }}</el-button>
                                        <el-button
                                            type="danger"
                                            size="small"
                                            @click="confirm"
                                        >
                                            {{ $t("common.yes") }}
                                        </el-button>
                                    </template>
                                </el-popconfirm>
                                <el-button class="notes-card-button" text circle @click="copyText(item.text)">
                                    <el-icon><CopyDocument/></el-icon>
                                </el-button>
                            </div>
                            <el-divider></el-divider>
                            <span class="notes-card-text-span">{{ item.text }}</span>
                        </el-card>
                    </el-timeline-item>
                </el-timeline>
            </el-scrollbar>
            <div class="notes-pagination-div">
                <el-pagination layout="prev, pager, next" v-model:current-page="page" @current-change="pageChange()" :page-size="20" :total="pageTotal" :pager-count="5" background />
            </div>
        </el-card>
        <el-drawer v-model="isEditDrawerOpen">
        <template #header>
            <span class="notes-manage-drawer-title">{{ $t("static.editNote") }}</span>
        </template>
        <el-form ref="notesFormRef" :model="notesForm" :rules="notesFormRules">
            <el-form-item prop="title">
                <h1>{{ $t("common.title") }}</h1>
                <el-input v-model="notesForm.title" maxlength="50" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item prop="text">
                <h1>{{ $t("common.content") }}</h1>
                <el-input v-model="notesForm.text" :rows="2" type="textarea" :autosize="{ minRows: 3 }" maxlength="2500" show-word-limit clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="clickEditNote(notesFormRef)" type="success">{{ $t("common.submit") }}</el-button>
            </el-form-item>
        </el-form>
    </el-drawer>
    </div>
</template>

<script lang="ts" setup>
import { ref,onMounted,h,reactive } from 'vue'
import { getDate,copyText } from '@/tools/tool'
import { getNotesListByUser,addNote,deleteNote,editNote } from '@/axios/api/notes';
import { ElMessage,ElNotification } from 'element-plus'
import type { FormInstance } from 'element-plus'
import i18n from '@/language';

const { t } = i18n.global

const searchInput = ref("") //搜索内容输入
const scrollbarHeight = ref((window.innerHeight - 230) + "px") //设置滚动条高度
const isNewNoteShow = ref(false) //新增笔记模块是否显示
const newNoteTimeShow = ref("") //新增笔记时显示的时间
const isEditDrawerOpen = ref(false) //编辑抽屉是否打开
const notesScrollBarRef = ref<HTMLElement | null>(null) //滚动条的ref 在onMounted后被赋值

const deleteConfirmTitle = ref(t("static.deleteConfirmTitle")) //删除确认框的标题文字
const page = ref(1) //页数
const pageTotal = ref(0) //总条数

const notesList:any = ref([]) //notes数据列表
const notesForm = reactive //添加 notes 的表单
({
    title: "",
    text: "",
})
const notesFormRef = ref<FormInstance>() //添加 notes 表单的 ref
const notesFormRules = reactive //添加 notes 表单的 rule
({
    title:
    [
        { required: true, message: t("rules.title"), trigger: 'blur' },
    ],
    text:
    [
        { required: true, message: t("rules.content"), trigger: 'blur' },
    ],
})
const notesEditUuid = ref("") //修改的note uuid

const getNotesListData = async () => //获取 notes 列表 (需登录)
{
    try
    {
        const params = 
        {
            searchInput: searchInput.value,
            page: page.value,
        }
        const resp = await getNotesListByUser(params)
        pageTotal.value = resp.data.total
        notesList.value = resp.data.notesList
    } catch {}
}

onMounted( () =>
{
    getNotesListData()
})

const clickSearch = () => //点击搜索
{
    getNotesListData()
}

const pageChange = () => //翻页
{
    getNotesListData()
}

const clickAddNote = () => //点击新增笔记
{
    newNoteTimeShow.value = getDate()
    notesForm.title = ""
    notesForm.text = ""
    isNewNoteShow.value = true
    notesScrollBarRef.value?.scrollTo({ top: 0, behavior: 'smooth' }) //将滚动条平滑滚动到顶部
}

const clickCancelAdd = () => //取消新增笔记
{
    isNewNoteShow.value = false
    notesForm.title = ""
    notesForm.text = ""
}

const clickEdit = (item) => //点击编辑按钮
{
    isNewNoteShow.value = false
    notesForm.title = item.title
    notesForm.text = item.text
    isEditDrawerOpen.value = true
    notesEditUuid.value = item.uuid
}

const doAddNoteConfirm = async () => //发送新增笔记请求
{
    try
    {
        const params =
        {
            title: notesForm.title,
            text: notesForm.text,
        }
        const resp = await addNote(params)
        if(resp.data.code == 200)
        {
            ElMessage({
                message: t("static.saveSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage({
                message: t("static.saveFailed"),
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
        isNewNoteShow.value = false
        notesForm.title = ""
        notesForm.text = ""
        getNotesListData()
    } catch {}
}

const clickAddNoteConfirm = async (formEl: FormInstance | undefined) => //点击新增 notes 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doAddNoteConfirm()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const doEditnote = async () =>
{
    try
    {
        const params =
        {
            uuid: notesEditUuid.value,
            title: notesForm.title,
            text: notesForm.text,
        }
        const resp = await editNote(params)
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
        else if(resp.data.code == 401)
        {
            ElMessage({
                message: t("static.editFailedBecauseDifferentUser"),
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
        isEditDrawerOpen.value = false
        notesForm.title = ""
        notesForm.text = ""
        getNotesListData()
    } catch {}
}

const clickEditNote = async (formEl: FormInstance | undefined) => //点击修改 notes 提交
{
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            doEditnote()
        } else {
            console.log('error submit!', fields)
        }
    })
}

const clickDelete = () => //点击删除笔记
{
    deleteConfirmTitle.value = t("static.deleteConfirmTitle") //重置一次删除警告提示语
}

const confirmDelete = async (item:any) => //确认删除
{
    try
    {
        const params = 
        {
            uuid: item.uuid
        }
        const resp = await deleteNote(params)
        if(resp.data.code == 200)
        {
            ElMessage
            ({
                message: t("static.deleteSuccess"),
                type: 'success',
            })
        }
        else if(resp.data.code == 400)
        {
            ElMessage
            ({
                message: t("static.deleteFailed"),
                type: 'warning',
            })
        }
        else if(resp.data.code == 401)
        {
            ElNotification
            ({
                title: t("common.noties"),
                message: h('i', { style: 'color: #F56C6C' }, t("static.deleteFailedBecauseDifferentUser")),
                offset: 50,
            })
        }
        else
        {
            ElMessage
            ({
                message: t("static.paramsError"),
                type: 'error',
            })
        }
        getNotesListData()
    } catch {}
}

const resetMinHeightAndMenu = () =>
{
    resetScrollbarHeight()
}

const resetScrollbarHeight = () => //重置滚动条高度(适应窗口大小)
{
    scrollbarHeight.value = (window.innerHeight - 230) + "px"
}

resetMinHeightAndMenu()

window.addEventListener('resize',resetMinHeightAndMenu) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.notes-card-title
{
    white-space: normal; /* 允许自动换行 */
    word-break: break-all; /* 截断单词进行换行 */
    font-size: 30px;
    font-weight: bold;
    margin: 0;
}

.notes-search-div
{
    margin-bottom: 10px;
}

.notes-add-button
{
    float: right;
}

.notes-timeline .el-timeline
{
    padding: 0px 20px 0px 20px;
}

.notes-timeline .el-divider--horizontal
{
    margin-top: 5px;
    margin-bottom: 10px;
}

.notes-pagination-div
{
    margin-top: 10px;
}

.notes-card-title
{
   font-weight: bold;
   font-size: 25px; 
}

.notes-card-buttons-div
{
    float: right;
}

.notes-card-button
{
    margin: 0 !important;
}

.notes-card-text-span
{
    white-space: normal; /* 允许自动换行 */
    word-break: break-all; /* 截断单词进行换行 */
    font-size: 15px;
    font-weight: bold;
}
</style>