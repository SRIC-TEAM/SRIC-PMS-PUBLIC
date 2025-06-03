import { downloadFile, get,post, postForm } from '@/axios/axios'

export function getResourcesCategoryManageDataList(params:any)
{
    return get('/resourcesManage/getResourcesCategoryManageDataList',params)
}

export function getResourcesCategoryList(params:any)
{
    return get('/resources/getResourcesCategoryList',params)
}

export function addOrUpdateResourcesCategory(params:any)
{
    return post('/resourcesManage/addOrUpdateResourcesCategory',params)
}

export function getResourcesManageList(params:any)
{
    return get('/resourcesManage/getResourcesManageList',params)
}

export function editResourcesMessage(params:any)
{
    return post('/resourcesManage/editResourcesMessage',params)
}

export function checkResourcesName(params:any)
{
    return get('/resourcesManage/checkResourcesName',params)
}

export function uploadResources(params: any,file: File)
{
    return postForm('/resourcesManage/uploadResources',params,file)
}

export function getResourcesList(params: any)
{
    return get('/resources/getResourcesList',params)
}

export function downloadResources(params: any)
{
    return downloadFile('/resources/downloadResources',params)
}