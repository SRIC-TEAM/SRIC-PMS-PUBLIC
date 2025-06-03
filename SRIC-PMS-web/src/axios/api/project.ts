import { get,post } from '@/axios/axios'

export function getProjectManageList(params:any)
{
    return get('/projectManage/getProjectManageList',params)
}

export function addOrUpdateProject(params:any)
{
    return post('/projectManage/addOrUpdateProject',params)
}

export function getProjectManageListWithUserList(params:any)
{
    return get('/projectManage/getProjectManageListWithUserList',params)
}

export function deleteProjectUser(params:any)
{
    return post('/projectManage/deleteProjectUser',params)
}

export function addProjectUser(params:any)
{
    return post('/projectManage/addProjectUser',params)
}

export function getProjectListWithUserList(params:any)
{
    return get('/project/getProjectListWithUserList',params)
}

export function getProjectByUuid(params:any)
{
    return get('/project/getProjectByUuid',params)
}

export function getProjectItemListByProject(params:any)
{
    return get('/projectItem/getProjectItemListByProject',params)
}

export function endProjectItem(params:any)
{
    return post('/projectItem/endProjectItem',params)
}

export function pickProjectItemUser(params:any)
{
    return post('/projectItem/pickProjectItemUser',params)
}

export function addProjectItem(params:any)
{
    return post('/projectItem/addProjectItem',params)
}

export function endProjectItemManage(params:any)
{
    return post('/projectItemManage/endProjectItemManage',params)
}

export function pickProjectItemUserManage(params:any)
{
    return post('/projectItemManage/pickProjectItemUserManage',params)
}

export function addProjectItemManage(params:any)
{
    return post('/projectItemManage/addProjectItemManage',params)
}

export function updateProjectItemTextManage(params:any)
{
    return post('/projectItemManage/updateProjectItemTextManage',params)
}