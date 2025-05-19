import { get, post, postForm } from '@/axios/axios'

export function getHComicTagManageDataList(params:any)
{
    return get('/hManage/getHComicTagManageDataList',params)
}

export function addOrUpdateHComicTag(params:any)
{
    return post('/hManage/addOrUpdateHComicTag',params)
}

export function getHVideoTagManageDataList(params:any)
{
    return get('/hManage/getHVideoTagManageDataList',params)
}

export function addOrUpdateHVideoTag(params:any)
{
    return post('/hManage/addOrUpdateHVideoTag',params)
}

export function getHPictureNumber(params:any)
{
    return get('/hManage/getHPictureNumber',params)
}

export function uploadPicture(params: any,file: File)
{
    return postForm('/hManage/uploadPicture',params,file)
}