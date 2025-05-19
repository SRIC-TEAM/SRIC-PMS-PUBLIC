package com.spring.springboot.resources.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.resources.pojo.Resources;
import com.spring.springboot.resources.pojo.ResourcesListRequestPojo;
import com.spring.springboot.resources.pojo.ResourcesListResponsePojo;
import com.spring.springboot.resources.service.ResourcesService;
import com.spring.springboot.tools.FileDownload;
import com.spring.springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * resources 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/resources")
public class ResourcesController
{
    @Value("${filesPath}") //获取application.properties中的全局变量
    private String filesPath;

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    UserService userService;

    @Autowired
    FileDownload fileDownloadService;

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表 分页
     * 先根据 token 获取用户
     * 通过用户获取用户权限
     * 通过用户权限 获取列表
     */
    @GetMapping("/getResourcesList")
    public ResourcesListResponsePojo getResourcesList(ResourcesListRequestPojo resourcesListRequest, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        if(tokenValue == null || tokenValue.isEmpty()) //若用户未登录 则默认权限等级为10
        {
            resourcesListRequest.setLevel(10);
        }
        else
        {
            Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
            int level = userService.getUserStatusByUuid(loginId.toString()); //根据 uuid 获取当前用户的权限等级
            resourcesListRequest.setLevel(level); //设置等级参数
        }

        ResourcesListResponsePojo resourcesListResponse = new ResourcesListResponsePojo();
        resourcesListResponse.setResourcesList(resourcesService.getResourcesListWithPermission(resourcesListRequest));
        resourcesListResponse.setTotal(resourcesService.getResourcesCountWithPermission(resourcesListRequest));
        return resourcesListResponse;
    }

    /**
     * @author SRIC
     *
     * 根据传入的 resources uuid 下载资源
     * 需根据 token 判断权限是否足够
     */
    @GetMapping("/downloadResources")
    public ResponseEntity<InputStreamResource> downloadResources(Resources resources, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        if(tokenValue == null || tokenValue.isEmpty()) //若用户未登录 则默认权限等级为10
        {
            resources.setLevel(10);
        }
        else
        {
            Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
            int level = userService.getUserStatusByUuid(loginId.toString()); //根据 uuid 获取当前用户的权限等级
            resources.setLevel(level); //设置等级参数
        }

        Resources resourcesData = resourcesService.getResourcesByUuid(resources); //根据传入的 uuid 获取数据库中的 resources 数据

        boolean permissionPassed = resources.getLevel() <= resourcesData.getLevel(); //若当前登录用户的权限等级 小于等于 资源所需的权限等级 则权限校验通过 (权限等级数值越小 权限越高)
        String filePath = filesPath + "/resources/" + resourcesData.getFilename();

        return fileDownloadService.downloadFile(filePath,permissionPassed);
    }
}
