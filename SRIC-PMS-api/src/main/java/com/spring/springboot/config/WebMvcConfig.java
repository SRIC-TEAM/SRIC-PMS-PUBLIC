package com.spring.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 * 匹配 非 api 路径 转发前端框架接管路由
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    /**
     * @author SRIC
     *
     * 指定静态资源的存储位置为 `classpath:/static/`，即项目的 `resources/static` 目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * @author SRIC
     *
     * 匹配所有不以 `/api` 开头的路径
     * 将这些路径请求转发到 `index.html`，由前端框架接管路由处理
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        // 将任何未匹配的路径都重定向到index.html
        registry.addViewController("/{spring:(?!api)[^\\\\.]*}").setViewName("forward:/index.html");
    }
}