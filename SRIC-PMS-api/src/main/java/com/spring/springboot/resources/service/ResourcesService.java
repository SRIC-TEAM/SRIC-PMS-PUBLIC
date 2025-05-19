package com.spring.springboot.resources.service;

import com.spring.springboot.resources.pojo.Resources;
import com.spring.springboot.resources.pojo.ResourcesListRequestPojo;
import com.spring.springboot.response.ResponseCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourcesService
{
    List<Resources> getResourcesList(ResourcesListRequestPojo resourcesListRequest);
    int getResourcesCount(ResourcesListRequestPojo resourcesListRequest);
    int editResourcesMessage(Resources resources);
    int checkResourcesName(String name);
    int uploadResources(MultipartFile file,Resources resources);
    List<Resources> getResourcesListWithPermission(ResourcesListRequestPojo resourcesListRequest);
    int getResourcesCountWithPermission(ResourcesListRequestPojo resourcesListRequest);
    Resources getResourcesByUuid(Resources resources);
}
