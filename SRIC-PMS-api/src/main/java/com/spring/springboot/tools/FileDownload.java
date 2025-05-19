package com.spring.springboot.tools;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 工具类
 * 文件下载相关
 */

@Service
public class FileDownload
{
    /**
     * 提供文件下载流式传输的功能
     * 通过传入的文件路径 提供流式下载传输
     * 传入 权限校验未通过 则返回权限不足状态码
     */
    public ResponseEntity<InputStreamResource> downloadFile(String filePath,boolean permissionPassed)
    {
        File file = new File(filePath);

        //若传入的权限校验为 false 未通过 则直接返回 403
        if(!permissionPassed)
        {
            return ResponseEntity.status(403).body(null);
        }

        // 检查文件是否存在
        if (!file.exists())
        {
            return ResponseEntity.notFound().build();
        }

        System.out.println("下载文件： " + file.getName());

        try
        {
            // 对文件名进行 URL 编码
            String encodedFilename = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8).replace("+", "%20"); // 修复 URLEncoder 编码空格的问题

            // 创建文件流和资源
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(fileInputStream);

            // 构建响应
            return ResponseEntity.ok()
                    .header("Access-Control-Expose-Headers","Content-Disposition")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .body(resource);
        }
        catch (FileNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).build();
        }
    }
}
