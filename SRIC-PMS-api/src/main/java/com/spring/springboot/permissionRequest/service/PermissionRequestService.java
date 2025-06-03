package com.spring.springboot.permissionRequest.service;

import com.spring.springboot.permissionRequest.pojo.PermissionRequest;
import com.spring.springboot.permissionRequest.pojo.PermissionRequestListRequestPojo;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

public interface PermissionRequestService
{
    List<PermissionRequest> getPermissionRequestList(PermissionRequestListRequestPojo permissionRequestListRequest);
    int getPermissionRequestCount();
    int addOrUpdatePermissionRequest(PermissionRequest permissionRequest);
    int getInterfacePermissionLevelByRequestMapping(String requestMapping);
}
