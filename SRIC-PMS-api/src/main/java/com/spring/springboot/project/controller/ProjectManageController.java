package com.spring.springboot.project.controller;

import com.spring.springboot.project.pojo.Project;
import com.spring.springboot.project.pojo.ProjectListRequestPojo;
import com.spring.springboot.project.pojo.ProjectListResponsePojo;
import com.spring.springboot.project.pojo.ProjectUserRelationPojo;
import com.spring.springboot.project.service.ProjectService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * project manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/projectManage")
public class ProjectManageController
{
    @Autowired
    ProjectService projectService;

    /**
     * @author SRIC
     *
     * 获取 project manage 数据列表 分页
     */
    @GetMapping("/getProjectManageList")
    public ProjectListResponsePojo getProjectManageList(ProjectListRequestPojo projectListRequest)
    {
        ProjectListResponsePojo projectListResponsePojo = new ProjectListResponsePojo();
        projectListResponsePojo.setProjectList(projectService.getProjectList(projectListRequest));
        projectListResponsePojo.setTotal(projectService.getProjectCount());
        return projectListResponsePojo;
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 project 数据
     */
    @PostMapping("/addOrUpdateProject")
    public ResponseCode addOrUpdateProject(@RequestBody Project permissionRequest)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(projectService.addOrUpdateProject(permissionRequest));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 获取 project manage 数据列表 分页
     * 携带 加入项目的用户列表
     */
    @GetMapping("/getProjectManageListWithUserList")
    public ProjectListResponsePojo getProjectManageListWithUserList(ProjectListRequestPojo projectListRequest)
    {
        ProjectListResponsePojo projectListResponsePojo = new ProjectListResponsePojo();
        projectListResponsePojo.setProjectList(projectService.getProjectListWithUserList(projectListRequest));
        projectListResponsePojo.setTotal(projectService.getProjectCount());
        return projectListResponsePojo;
    }

    /**
     * @author SRIC
     *
     * 删除一条 relation 数据
     */
    @PostMapping("/deleteProjectUser")
    public ResponseCode deleteProjectUser(@RequestBody ProjectUserRelationPojo projectUserRelation)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(projectService.deleteProjectUserRelation(projectUserRelation));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 添加一条 relation 数据
     */
    @PostMapping("/addProjectUser")
    public ResponseCode addProjectUser(@RequestBody ProjectUserRelationPojo projectUserRelation)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(projectService.insertProjectUserRelation(projectUserRelation));
        return responseCode;
    }
}
