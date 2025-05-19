<template>
    <div class="common-body-set-width">
        <el-container>
            <el-header>
                <p class="HfilesVideoPlayer-header-name">{{ videoData.name }}</p>
            </el-header>
            <el-container>
                <el-aside :width="asideWidth">
                    <div>
                        <p class="HfilesVideoPlayer-title">{{ $t("static.videoCode") }}</p>
                        <p class="HfilesVideoPlayer-text">{{ videoData.number }}</p>
                        <p class="HfilesVideoPlayer-title">{{ $t("static.filename") }}</p>
                        <p class="HfilesVideoPlayer-text">{{ videoData.filename }}</p>
                        <p class="HfilesVideoPlayer-title">{{ $t("static.category") }}</p>
                        <el-tag
                        class="HfilesVideoPlayer-category-tag"
                        effect="plain"
                        :type="videoData.mosaicType"
                        >
                        <span class="HfilesVideo-card-tag-text">{{ videoData.mosaicText }}</span>
                        </el-tag>
                        <el-tag
                        class="HfilesVideoPlayer-category-tag"
                        effect="plain"
                        :type="videoData.categoryType"
                        round
                        >
                        <span class="HfilesVideo-card-tag-text">{{ videoData.categoryText }}</span>
                        </el-tag>
                        <p class="HfilesVideoPlayer-title">TAGS:</p>
                        <el-tag
                        v-for="(item,i) in videoData.hvideoTagList"
                        :key="i"
                        class="HfilesVideoPlayer-tag"
                        effect="dark"
                        type="info"
                        size="small"
                        >
                            <span class="HfilesVideo-card-tag-text">{{ item.name }}</span>
                        </el-tag>
                    </div>
                </el-aside>
                <el-main>
                    <div class="video-player">
                        <div id="dplayer"></div>
                    </div>
                </el-main>
            </el-container>
        </el-container>
        <div v-if="isMobile"> <!-- 一个重复的信息菜单 用于在页面过于狭窄时使用 -->
            <p class="HfilesVideoPlayer-title">{{ $t("static.videoCode") }}</p>
            <p class="HfilesVideoPlayer-text">{{ videoData.number }}</p>
            <p class="HfilesVideoPlayer-title">{{ $t("static.filename") }}</p>
            <p class="HfilesVideoPlayer-text">{{ videoData.filename }}</p>
            <p class="HfilesVideoPlayer-title">{{ $t("static.category") }}</p>
            <el-tag
            class="HfilesVideoPlayer-category-tag"
            effect="plain"
            :type="videoData.mosaicType"
            >
            <span class="HfilesVideo-card-tag-text">{{ videoData.mosaicText }}</span>
            </el-tag>
            <el-tag
            class="HfilesVideoPlayer-category-tag"
            effect="plain"
            :type="videoData.categoryType"
            round
            >
            <span class="HfilesVideo-card-tag-text">{{ videoData.categoryText }}</span>
            </el-tag>
            <p class="HfilesVideoPlayer-title">TAGS:</p>
            <el-tag
            v-for="(item,i) in videoData.hvideoTagList"
            :key="i"
            class="HfilesVideoPlayer-tag"
            effect="dark"
            type="info"
            size="small"
            >
                <span class="HfilesVideo-card-tag-text">{{ item.name }}</span>
            </el-tag>
        </div>
    </div>
</template>

<script lang="ts" setup>
import axios from 'axios';
import DPlayer from 'dplayer';
import { onMounted,ref } from 'vue';
import { useRoute } from 'vue-router'
import { getHVideoById } from "@/axios/api/hVideo"
import i18n from '@/language';

const { t } = i18n.global

const route = useRoute()

let dp:any;

const videoUrl = axios.defaults.baseURL + "/static/hFile/hVideo/" + route.query.video + ".mp4" //设置播放视频所需url
let videoData:any = ref({})
let asideWidth:any = ref("200px") //左侧显示信息栏的宽度
let isMobile:any = ref(false) //窗口是否过小（移动端视角）

const initPlayer = () =>
{
    dp = new DPlayer
    ({
        container: document.getElementById('dplayer'),
        autoplay: true,//自动播放
        video: 
        {
            url: videoUrl,
        },
    });
}

const getHVideoData = async () =>
{
    try
    {
        const resp = await getHVideoById({id:route.query.video})
        videoData.value = resp.data
        if(videoData.value.mosaic == 1)
        {
            videoData.value.mosaicType = "success"
            videoData.value.mosaicText = t("h.noMosaic")
        }
        else if(videoData.value.mosaic == 2)
        {
            videoData.value.mosaicType = "info"
            videoData.value.mosaicText = t("h.mosaic")
        }
        else
        {
            videoData.value.mosaicType = "info"
            videoData.value.mosaicText = t("static.categoryError")
        }

        if(videoData.value.category == 1)
        {
            videoData.value.categoryType = "danger"
            videoData.value.categoryText = "REAL"
        }
        else if(videoData.value.category == 2)
        {
            videoData.value.categoryType = "primary"
            videoData.value.categoryText = "3D MMD"
        }
        else if(videoData.value.category == 3)
        {
            videoData.value.categoryType = "warning"
            videoData.value.categoryText = "2D"
        }
        else
        {
            videoData.value.mosaicType = "info"
            videoData.value.mosaicText = t("static.categoryError")
        }
    } catch {}
}

onMounted(async () => 
{
    initPlayer()
    getHVideoData()
})

const windowSizeChanged = () => //窗口大小改变
{
    if(window.innerWidth < 800) //在窗口过小时折叠菜单栏
    {
        isMobile.value = true
        asideWidth.value = "0px"
    }
    else
    {
        isMobile.value = false
        asideWidth.value = "200px"
    }
}

windowSizeChanged()

window.addEventListener('resize',windowSizeChanged) //监听窗口变动
</script>

<style>
@import '@/css/common.css';

.HfilesVideoPlayer-header-name
{
    margin: 0;
    margin-left: 50px;
    font-weight: bold;
    font-size: 40px;
    white-space: nowrap; /* 防止换行 */
    overflow: hidden;    /* 隐藏溢出的文本 */
    text-overflow: ellipsis; /* 显示省略号 */
}

.HfilesVideoPlayer-title
{
    margin: 20px 0px 0px 0px;
    font-size: 12px;
}

.HfilesVideoPlayer-text
{
    margin: 0px 0px 20px 10px;
    font-weight: bold;
    font-size: 20px;
}

.HfilesVideoPlayer-category-tag
{
    margin: 10px 0px 0px 10px;
    font-weight: bold;
}

.HfilesVideoPlayer-tag
{
    margin: 10px 0px 0px 10px;
    font-weight: bold;
}

.HfilesVideoPlayer-add-select
{
    margin-top: 10px;
}

.HfilesVideoPlayer-add-button
{
    margin-top: 10px;
}

.HfilesVideoPlayer-add-button-text
{
    font-weight: bold;
}

.video-player
{
    width: 100%;
    position: relative;
    padding-bottom: 56.25%; /* 16:9 ratio */
}

#dplayer
{
    width: 100%;
    height: 100%;
    position: absolute;
}

.dplayer-video-wrap
{
    position: absolute;
    width: 100%;
    height: 100%;
}

</style>