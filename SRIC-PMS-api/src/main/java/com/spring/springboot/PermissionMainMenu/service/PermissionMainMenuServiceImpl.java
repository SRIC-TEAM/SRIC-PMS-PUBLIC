package com.spring.springboot.PermissionMainMenu.service;

import com.spring.springboot.PermissionMainMenu.mapper.PermissionMainMenuMapper;
import com.spring.springboot.PermissionMainMenu.pojo.PermissionMainMenu;
import com.spring.springboot.PermissionMainMenu.pojo.PermissionMainMenuListRequestPojo;
import com.spring.springboot.PermissionMainMenu.service.PermissionMainMenuService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * permission main menu 服务类
 * - - - - - - - - - -
 */

@Service
public class PermissionMainMenuServiceImpl implements PermissionMainMenuService
{
    @Autowired
    PermissionMainMenuMapper permissionMainMenuMapper;

    /**
     * @author SRIC
     *
     * 获取 permission main menu 数据列表 分页
     */
    public List<PermissionMainMenu> getPermissionMainMenuList(PermissionMainMenuListRequestPojo permissionMainMenuListRequest)
    {
        permissionMainMenuListRequest.setLimitSize(20); //一页获取20条数据
        permissionMainMenuListRequest.setLimitBefore((permissionMainMenuListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return permissionMainMenuMapper.getPermissionMainMenuList(permissionMainMenuListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 permission main menu 数据列表数量
     */
    public int getPermissionMainMenuCount()
    {
        return permissionMainMenuMapper.getPermissionMainMenuCount();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 permission main menu
     * 若修改数据条数不为 0 则修改成功
     * 若传入 uuid 则为修改
     * 传入 uuid 为空则为添加
     */
    public int addOrUpdatePermissionMainMenu(PermissionMainMenu permissionMainMenu)
    {
        if(permissionMainMenuMapper.getPermissionMainMenuCountByName(permissionMainMenu) > 0) //若已有重复的名称
        {
            return 400;
        }
        else
        {
            if(permissionMainMenu.getUuid().isEmpty())
            {
                permissionMainMenuMapper.insertPermissionMainMenu(permissionMainMenu);
            }
            else
            {
                permissionMainMenuMapper.updatePermissionMainMenu(permissionMainMenu);
            }
            return 200;
        }
    }

    /**
     * @author SRIC
     *
     * 获取菜单列表 根据权限等级进行显示
     */
    public List<PermissionMainMenu> getPermissionMainMenu()
    {
        return permissionMainMenuMapper.getPermissionMainMenu();
    }
}
