package com.spring.springboot.project.service;

import com.spring.springboot.project.mapper.ProjectMapper;
import com.spring.springboot.project.pojo.Project;
import com.spring.springboot.project.pojo.ProjectListRequestPojo;
import com.spring.springboot.project.pojo.ProjectUserRelationPojo;
import com.spring.springboot.response.ResponseCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * project 服务类
 * - - - - - - - - - -
 */

@Service
public class ProjectServiceImpl implements ProjectService
{
    @Autowired
    ProjectMapper projectMapper;

    /**
     * @author SRIC
     *
     * 获取 project 数据列表 分页
     */
    @Override
    public List<Project> getProjectList(ProjectListRequestPojo projectListRequest)
    {
        projectListRequest.setLimitSize(20); //一页获取20条数据
        projectListRequest.setLimitBefore((projectListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return projectMapper.getProjectList(projectListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 project 数据列表数量
     */
    @Override
    public int getProjectCount()
    {
        return projectMapper.getProjectCount();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 project
     * 若修改数据条数不为 0 则修改成功
     * 若传入 uuid 则为修改
     * 传入 uuid 为空则为添加
     */
    @Override
    public int addOrUpdateProject(Project project)
    {
        if(projectMapper.getProjectCountByName(project) > 0) //若已有重复的名称
        {
            return 400;
        }
        else
        {
            if(project.getUuid().isEmpty())
            {
                projectMapper.insertProject(project);
            }
            else
            {
                projectMapper.updateProject(project);
            }
            return 200;
        }
    }

    /**
     * @author SRIC
     *
     * 获取 project 数据列表 分页
     * 每一条数据中 携带已加入项目的用户数据
     */
    @Override
    public List<Project> getProjectListWithUserList(ProjectListRequestPojo projectListRequest)
    {
        projectListRequest.setLimitSize(20); //一页获取20条数据
        projectListRequest.setLimitBefore((projectListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return projectMapper.getProjectListWithUserList(projectListRequest);
    }

    /**
     * @author SRIC
     *
     * 删除一条 relation 数据
     */
    @Override
    public int deleteProjectUserRelation(ProjectUserRelationPojo projectUserRelation)
    {
        if(projectMapper.deleteProjectUserRelation(projectUserRelation) > 0) //若删除数据条数大于 0 则删除成功
        {
            return 200;
        }
        else
        {
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 添加一条 relation 数据
     */
    @Override
    public int insertProjectUserRelation(ProjectUserRelationPojo projectUserRelation)
    {
        if(projectMapper.getRelationByProjectAndUser(projectUserRelation) > 0)
        {
            return 400;
        }
        if(projectMapper.insertProjectUserRelation(projectUserRelation) > 0)
        {
            return 200;
        }
        else
        {
            return 500;
        }
    }

    /**
     * @author SRIC
     *
     * 获取 project 数据列表 分页
     * 每一条数据中 携带已加入项目的用户数据
     * 根据用户 uuid 获取用户已加入的项目列表
     */
    @Override
    public List<Project> getProjectListWithUserListByUser(ProjectListRequestPojo projectListRequest)
    {
        projectListRequest.setLimitSize(20); //一页获取20条数据
        projectListRequest.setLimitBefore((projectListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return projectMapper.getProjectListWithUserListByUser(projectListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 project 数据
     * 根据 project uuid 获取一条数据
     * 及对应的 用户列表
     */
    @Override
    public Project getProjectByUuidWithUserList(String uuid)
    {
        return projectMapper.getProjectByUuidWithUserList(uuid);
    }

    /**
     * @author SRIC
     *
     * 检查 relation
     * 检查相应的 project 是否已有 user
     * 返回 查询到的数据条数
     */
    @Override
    public int checkProjectUser(String userUuid,String projectUuid)
    {
        ProjectUserRelationPojo projectUserRelation = new ProjectUserRelationPojo(); //创建一个 pojo 用于传参
        projectUserRelation.setUserUuid(userUuid); //设置其中的用户信息
        projectUserRelation.setProjectUuid(projectUuid); //设置其中的项目信息
        return projectMapper.getRelationByProjectAndUser(projectUserRelation);
    }
}
