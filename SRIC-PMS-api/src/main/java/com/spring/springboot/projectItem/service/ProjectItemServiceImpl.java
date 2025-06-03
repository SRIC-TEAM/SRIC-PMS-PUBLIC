package com.spring.springboot.projectItem.service;

import com.spring.springboot.projectItem.mapper.ProjectItemMapper;
import com.spring.springboot.projectItem.pojo.ProjectItem;
import com.spring.springboot.projectItem.pojo.ProjectItemListRequestPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * project item 服务类
 * - - - - - - - - - -
 */

@Service
public class ProjectItemServiceImpl implements ProjectItemService
{
    @Autowired
    ProjectItemMapper projectItemMapper;

    /**
     * @author SRIC
     *
     * 获取 project 下的 project item 数据列表 分页
     */
    @Override
    public List<ProjectItem> getProjectItemList(ProjectItemListRequestPojo projectItemListRequest)
    {
        projectItemListRequest.setLimitSize(20); //一页获取20条数据
        projectItemListRequest.setLimitBefore((projectItemListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return projectItemMapper.getProjectItemList(projectItemListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 project 下的 project item 数据列表 数量
     */
    @Override
    public int getProjectItemCount(ProjectItemListRequestPojo projectItemListRequest)
    {
        return projectItemMapper.getProjectItemCount(projectItemListRequest);
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据 ended 为 1(已完成)
     */
    @Override
    public int endProjectItem(ProjectItem projectItem)
    {
        projectItem.setIsEnded("1"); //需要将 ended 设置为 1
        if(projectItemMapper.updateProjectItemEnded(projectItem) > 0)
        {
            return 200; //成功
        }
        else
        {
            return 400; //失败
        }
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据中的 assignUser 数据
     */
    @Override
    public int pickProjectItemUser(ProjectItem projectItem)
    {
        if(projectItemMapper.updateProjectItemAssignUserUuid(projectItem) > 0)
        {
            return 200; //成功
        }
        else
        {
            return 400; //失败
        }
    }

    /**
     * @author SRIC
     *
     * 新增一条 project item 数据、
     */
    @Override
    public int addProjectItem(ProjectItem projectItem)
    {
        if(projectItemMapper.insertProjectItem(projectItem) > 0)
        {
            return 200; //成功
        }
        else
        {
            return 400; //失败
        }
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据 ended
     */
    @Override
    public int updateProjectItemEnded(ProjectItem projectItem)
    {
        if(projectItemMapper.updateProjectItemEnded(projectItem) > 0)
        {
            return 200; //成功
        }
        else
        {
            return 400; //失败
        }
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据 title text
     */
    @Override
    public int updateProjectItemText(ProjectItem projectItem)
    {
        if(projectItemMapper.updateProjectItemText(projectItem) > 0)
        {
            return 200; //成功
        }
        else
        {
            return 400; //失败
        }
    }
}
