package com.spring.springboot.resourcesCategory.controller;

import com.spring.springboot.resourcesCategory.pojo.ResourcesCategoryManageData;
import com.spring.springboot.resourcesCategory.service.ResourcesCategoryService;
import com.spring.springboot.resourcesCategory.pojo.ResourcesCategory;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * resources category 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/resourcesManage")
public class ResourcesCategoryManageController
{
    @Autowired
    ResourcesCategoryService resourcesCategoryService;

    /**
     * @author SRIC
     *
     * 获取 resources category 列表
     * 并且获取 category 下 resources 的条数
     */
    @GetMapping("/getResourcesCategoryManageDataList")
    public List<ResourcesCategoryManageData> getResourcesCategoryManageDataList()
    {
        return resourcesCategoryService.getResourcesCategoryManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 resources category
     */
    @PostMapping("/addOrUpdateResourcesCategory")
    public ResponseCode addOrUpdateResourcesCategory(@RequestBody ResourcesCategory resourcesCategory)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(resourcesCategoryService.addOrUpdateResourcesCategory(resourcesCategory));
        return responseCode;
    }
}
