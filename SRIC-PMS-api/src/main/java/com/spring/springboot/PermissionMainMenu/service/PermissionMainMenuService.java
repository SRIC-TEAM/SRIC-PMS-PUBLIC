package com.spring.springboot.PermissionMainMenu.service;

import com.spring.springboot.PermissionMainMenu.pojo.PermissionMainMenu;
import com.spring.springboot.PermissionMainMenu.pojo.PermissionMainMenuListRequestPojo;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

public interface PermissionMainMenuService
{
    List<PermissionMainMenu> getPermissionMainMenuList(PermissionMainMenuListRequestPojo permissionMainMenuListRequest);
    int getPermissionMainMenuCount();
    int addOrUpdatePermissionMainMenu(PermissionMainMenu permissionMainMenu);
    List<PermissionMainMenu> getPermissionMainMenu();
}
