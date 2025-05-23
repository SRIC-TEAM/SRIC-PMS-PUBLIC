package com.spring.springboot.permissionRequest.service;

import com.spring.springboot.permissionRequest.mapper.PermissionRequestMapper;
import com.spring.springboot.permissionRequest.pojo.PermissionRequest;
import com.spring.springboot.permissionRequest.pojo.PermissionRequestListRequestPojo;
import com.spring.springboot.permissionRequest.service.PermissionRequestService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * permission request 服务类
 * - - - - - - - - - -
 */

@Service
public class PermissionRequestServiceImpl implements PermissionRequestService
{
    @Autowired
    PermissionRequestMapper permissionRequestMapper;

    /**
     * @author SRIC
     *
     * 获取 permission request 数据列表 分页
     */
    @Override
    public List<PermissionRequest> getPermissionRequestList(PermissionRequestListRequestPojo permissionRequestListRequest)
    {
        permissionRequestListRequest.setLimitSize(20); //一页获取20条数据
        permissionRequestListRequest.setLimitBefore((permissionRequestListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return permissionRequestMapper.getPermissionRequestList(permissionRequestListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 permission request 数据列表数量
     */
    @Override
    public int getPermissionRequestCount()
    {
        return permissionRequestMapper.getPermissionRequestCount();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 permission request
     * 若修改数据条数不为 0 则修改成功
     * 若传入 uuid 则为修改
     * 传入 uuid 为空则为添加
     */
    @Override
    public int addOrUpdatePermissionRequest(PermissionRequest permissionRequest)
    {
        if(permissionRequestMapper.getPermissionRequestCountByRequestMapping(permissionRequest) > 0) //若已有重复的名称
        {
            return 400;
        }
        else
        {
            if(permissionRequest.getUuid().isEmpty())
            {
                permissionRequestMapper.insertPermissionRequest(permissionRequest);
            }
            else
            {
                permissionRequestMapper.updatePermissionRequest(permissionRequest);
            }
            return 200;
        }
    }

    /**
     * @author SRIC
     *
     * 根据 request mapping 获取接口权限等级
     */
    public int getInterfacePermissionLevelByRequestMapping(String requestMapping)
    {
        PermissionRequest permissionRequest = permissionRequestMapper.getPermissionRequestByRequestMapping(requestMapping);
        if(permissionRequest == null) //若未获取到数据 则将其设置为需要最高权限
        {
            return 1;
        }
        else
        {
            return permissionRequest.getLevel();
        }
    }
}
