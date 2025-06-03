package com.spring.springboot.resourcesCategory.controller;

import com.spring.springboot.resourcesCategory.service.ResourcesCategoryService;
import com.spring.springboot.resourcesCategory.pojo.ResourcesCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * - - - - - - - - - -
 * library category 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/resources")
public class ResourcesCategoryController
{
    @Autowired
    ResourcesCategoryService resourcesCategoryService;

    /**
     * @author SRIC
     *
     * 获取 resources category 列表
     */
    @GetMapping("/getResourcesCategoryList")
    public List<ResourcesCategory> getResourcesCategoryList()
    {
        return resourcesCategoryService.getResourcesCategoryList();
    }
}
