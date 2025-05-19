package com.spring.springboot.resourcesCategory.service;

import com.spring.springboot.resourcesCategory.pojo.ResourcesCategory;
import com.spring.springboot.resourcesCategory.pojo.ResourcesCategoryManageData;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

public interface ResourcesCategoryService
{
    List<ResourcesCategory> getResourcesCategoryList();
    List<ResourcesCategoryManageData> getResourcesCategoryManageDataList();
    int addOrUpdateResourcesCategory(ResourcesCategory resourcesCategory);
}
