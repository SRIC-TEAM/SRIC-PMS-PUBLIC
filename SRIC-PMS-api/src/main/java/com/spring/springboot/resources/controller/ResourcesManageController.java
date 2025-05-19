package com.spring.springboot.resources.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.resources.pojo.Resources;
import com.spring.springboot.resources.pojo.ResourcesListRequestPojo;
import com.spring.springboot.resources.pojo.ResourcesListResponsePojo;
import com.spring.springboot.resources.service.ResourcesService;
import com.spring.springboot.response.ResponseCode;
import com.spring.springboot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * - - - - - - - - - -
 * resources manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/resourcesManage")
public class ResourcesManageController
{
    @Autowired
    ResourcesService resourcesService;

    /**
     * @author SRIC
     *
     * 获取 resources manage 数据列表 分页
     */
    @GetMapping("/getResourcesManageList")
    public ResourcesListResponsePojo getResourcesManageList(ResourcesListRequestPojo resourcesListRequest)
    {
        ResourcesListResponsePojo resourcesListResponse = new ResourcesListResponsePojo();
        resourcesListResponse.setResourcesList(resourcesService.getResourcesList(resourcesListRequest));
        resourcesListResponse.setTotal(resourcesService.getResourcesCount(resourcesListRequest));
        return resourcesListResponse;
    }

    /**
     * @author SRIC
     *
     * 编辑一条 resources 数据
     */
    @PostMapping("/editResourcesMessage")
    public ResponseCode editResourcesMessage(@RequestBody Resources resources)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(resourcesService.editResourcesMessage(resources));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 检查 resources 是否存在重名
     */
    @GetMapping("/checkResourcesName")
    public ResponseCode checkResourcesName(String name)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(resourcesService.checkResourcesName(name));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 上传一条 resources 数据
     */
    @PostMapping("/uploadResources")
    public ResponseCode uploadResources(@RequestParam("file") MultipartFile file,@ModelAttribute Resources resources)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(resourcesService.uploadResources(file,resources));
        return responseCode;
    }
}
