import axios, { AxiosResponse } from 'axios';
import { ElNotification } from 'element-plus'
import { h } from 'vue'
import i18n from '@/language';
const { t } = i18n.global

// 请求拦截器
axios.interceptors.request.use(
    (config :any) => {
        // 在请求头中添加一个token
        const token = localStorage.getItem("token")
        config.headers['Authorization'] = token;
        return config;
    },
    (error) => {
        // 处理请求错误
        return Promise.reject(error);
    }
);

// 响应拦截器
axios.interceptors.response.use(
    (response :any) => {
        console.log('响应拦截器:', response);
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 403) //403 权限不足
        {
            ElNotification
            ({
                title: t("common.noties"),
                message: h('i', { style: 'color: #c93f38' }, t("static.permissionDenied")),
                offset: 50,
            })
            return Promise.resolve(error);
        }
        // 处理响应错误
        return Promise.reject(error);
    }
);

//get 请求
export function get(url: string, params: any): Promise<AxiosResponse<any>> {
    return axios({
        method: 'get',
        url: "/api" + `${url}`,
        params: params,
    });
}

//post 请求 使用 json 传参
export function post(url: string, params: any): Promise<AxiosResponse<any>> {
    return axios({
        method: 'post',
        url: "/api" + `${url}`,
        data: params,
        headers: { "Content-Type": "application/json" }
    });
}

//post form 请求 使用 formData 传参
export function postForm(url: string, params: any, file: File): Promise<AxiosResponse<any>> {
    const formData = new FormData();

    // 将额外的参数作为 JSON 添加到 FormData 中
    Object.keys(params).forEach((key) => {
        formData.append(key, params[key]);
    });

    // 将文件添加到 FormData 中
    formData.append('file', file);

    return axios({
        method: 'post',
        url: "/api" + `${url}`,
        data: formData,
        headers: { "Content-Type": "multipart/form-data" }
    });
}

//download 请求
export function downloadFile(url: string, params: any): Promise<AxiosResponse<any>> {
    return axios({
        method: 'get',
        url: "/api" + `${url}`,
        params: params,
        responseType: 'blob', // 设置响应类型为 Blob
    });
}