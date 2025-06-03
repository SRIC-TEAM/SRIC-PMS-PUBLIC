package com.spring.springboot.project.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.project.pojo.Project;
import com.spring.springboot.project.pojo.ProjectListRequestPojo;
import com.spring.springboot.project.pojo.ProjectListResponsePojo;
import com.spring.springboot.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * project 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController
{
    @Autowired
    ProjectService projectService;

    /**
     * @author SRIC
     *
     * 获取 project manage 数据列表 分页
     * 携带 加入项目的用户列表
     * 根据当前登录用户的 token 获取用户 uuid 获取当前用户加入的项目列表
     */
    @GetMapping("/getProjectListWithUserList")
    public ProjectListResponsePojo getProjectListWithUserList(ProjectListRequestPojo projectListRequest, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        if(tokenValue == null || tokenValue.isEmpty()) //若用户未登录 返回空对象
        {
            return new ProjectListResponsePojo();
        }
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        projectListRequest.setUserUuid(loginId.toString()); //将 uuid 插入参数对象 用于查询列表
        ProjectListResponsePojo projectListResponsePojo = new ProjectListResponsePojo();
        projectListResponsePojo.setProjectList(projectService.getProjectListWithUserListByUser(projectListRequest));
        projectListResponsePojo.setTotal(projectService.getProjectCount());
        return projectListResponsePojo;
    }

    /**
     * @author SRIC
     *
     * 获取 project 数据
     * 根据 project uuid 获取一条数据
     * 及对应的 用户列表
     */
    @GetMapping("/getProjectByUuid")
    public Project getProjectByUuid(Project project)
    {
        return projectService.getProjectByUuidWithUserList(project.getUuid());
    }
}
