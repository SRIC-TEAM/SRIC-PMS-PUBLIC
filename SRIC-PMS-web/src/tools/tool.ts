import { ElNotification,ElMessage } from 'element-plus'
import i18n from '@/language';
const { t } = i18n.global
import { h } from 'vue'

//根据传入的权限等级获取颜色配置
export const getStatusColor = (tag: number): string | undefined => {
    switch(tag) {
        case 0: return "color: #ff8f8f";
        case 1: return "color: #c93f38";
        case 2: return "color: #e56e24";
        case 3: return "color: #eec400";
        case 4: return "color: #a59344";
        case 5: return "color: #76b583";
        case 6: return "color: #008a60";
        case 7: return "color: #65a7dd";
        case 8: return "color: #00035b";
        case 9: return "color: #7249d6";
        case 10: return "color: #9c52f2";
        default: return undefined;
    }
};

//根据传入的权限等级获取颜色
export const getStatusColorCode = (tag: number): string | undefined => {
    switch(tag) {
        case 0: return "#ff8f8f";
        case 1: return "#c93f38";
        case 2: return "#e56e24";
        case 3: return "#eec400";
        case 4: return "#a59344";
        case 5: return "#76b583";
        case 6: return "#008a60";
        case 7: return "#65a7dd";
        case 8: return "#00035b";
        case 9: return "#7249d6";
        case 10: return "#9c52f2";
        default: return undefined;
    }
};

//获取当前时间 格式为 yyyy-mm-dd hh-mm-ss
export const getTime = () =>
{
    // 获取当前时间
    const now = new Date();

    // 获取各个时间部分并进行格式化
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份从0开始，所以需要加1
    const date = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    // 组装成所需格式
    return `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
}

//获取当前日期 格式为 yyyy-mm-dd
export const getDate = () =>
{
    // 获取当前时间
    const now = new Date();

    // 获取各个时间部分并进行格式化
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份从0开始，所以需要加1
    const date = String(now.getDate()).padStart(2, '0');

    // 组装成所需格式
    return `${year}-${month}-${date}`;
}

//将传输回来的响应与文件流进行下载
export const downloadFileFromResponse = (resp:any) =>
{
    // 将响应数据包装为 Blob
    const blob = new Blob([resp.data], {
        type: resp.headers['content-type'] || 'application/octet-stream',
    });

    // 创建文件的临时 URL
    const url = window.URL.createObjectURL(blob);

    // 动态创建 <a> 标签用于下载
    const link = document.createElement('a');
    link.href = url;

    // 从响应头中获取文件名
    const contentDisposition = resp.headers['content-disposition'];
    let filename = 'downloaded-file';
    if (contentDisposition) {
        const filenameMatch = contentDisposition.match(/filename\*?=['"]?([^;\n]+)['"]?/);
        if (filenameMatch) {
            filename = decodeURIComponent(filenameMatch[1].replace("UTF-8''", ''));
        }
    }

    link.setAttribute('download', filename); // 设置文件名
    document.body.appendChild(link);
    link.click(); // 模拟点击触发下载
    document.body.removeChild(link); // 清理 DOM

    // 释放临时 URL
    window.URL.revokeObjectURL(url);

    ElNotification
    ({
        title: t("common.noties"),
        message: h('i', { style: 'color: #67C23A' }, t("static.downloadFinished")),
        offset: 50,
    })
}

//使用浏览器 navigator.clipboard 命令复制文字 若浏览器不支持则使用 document.execCommand('Copy') 复制
export const copyText = (text:any) =>
{
    if (navigator.clipboard)
    {
        navigator.clipboard.writeText(text).then(() => {
            ElMessage
            ({
                message: t("static.copySuccess"),
                type: 'success',
            })
        }).catch(err => {
            ElMessage
            ({
                message: t("static.copyError"),
                type: 'error',
            })
        })
    }
    else
    {
        const oInput = document.createElement('input')
        oInput.value = text
        document.body.appendChild(oInput)
        oInput.select() //选择对象
        document.execCommand('Copy') //执行浏览器复制命令
        oInput.remove()
        ElMessage
        ({
            message: t("static.copySuccess"),
            type: 'success',
        })

        ElNotification
        ({
            title: t("common.noties"),
            message: h('div', {},
            [
                h('i', { style: 'color: #F56C6C' }, t("static.copyNotCompatible")),
                h('br'), // 插入换行符
                h('i', { style: 'color: #67C23A' }, t("static.copyOldApiTrying"))
            ]),
            offset: 50,
        })
    }
    ElNotification
    ({
        title: t("common.noties"),
        message: h('i', { style: 'color: #E6A23C' }, t("common.copy") + " : \"" + text + "\""),
        offset: 150,
    })
}

// 将文件转换为 Base64
export const toBase64 = (file) =>
    new Promise<string>((resolve, reject) => 
    {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result as string);
        reader.onerror = (error) => reject(error);
        reader.readAsDataURL(file);
    });