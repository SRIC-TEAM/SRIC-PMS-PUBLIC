package com.spring.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 * 将本地静态文件映射在 url 上
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer //静态资源映射
{
    //获取application.properties中的全局变量
    @Value("${filesPath}")
    private String filesPath;
    @Value("${hFilesPath}")
    private String hFilesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/hFile/hComic/**").addResourceLocations("file:" + hFilesPath + "/hComic/");
        registry.addResourceHandler("/static/hFile/hVideo/**").addResourceLocations("file:" + hFilesPath + "/hVideo/");
        registry.addResourceHandler("/static/hFile/hPicture/**").addResourceLocations("file:" + hFilesPath + "/hPicture/");
        registry.addResourceHandler("/static/file/userAvatar/**").addResourceLocations("file:" + filesPath + "/userAvatar/");
    }
}
