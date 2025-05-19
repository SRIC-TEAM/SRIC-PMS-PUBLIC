package com.spring.springboot.projectItem.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.project.service.ProjectService;
import com.spring.springboot.projectItem.pojo.ProjectItem;
import com.spring.springboot.projectItem.pojo.ProjectItemListRequestPojo;
import com.spring.springboot.projectItem.pojo.ProjectItemListResponsePojo;
import com.spring.springboot.projectItem.service.ProjectItemService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * project item 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/projectItem")
public class ProjectItemController
{
    @Autowired
    ProjectItemService projectItemService;

    @Autowired
    ProjectService projectService;

    /**
     * @author SRIC
     *
     * 获取 project item 数据列表 分页
     * 根据传入的 project uuid 获取
     */
    @GetMapping("/getProjectItemListByProject")
    public ProjectItemListResponsePojo getProjectItemListByProject(ProjectItemListRequestPojo projectItemListRequest)
    {
        ProjectItemListResponsePojo projectItemListResponse = new ProjectItemListResponsePojo();
        projectItemListResponse.setProjectItemList(projectItemService.getProjectItemList(projectItemListRequest));
        projectItemListResponse.setTotal(projectItemService.getProjectItemCount(projectItemListRequest));
        return projectItemListResponse;
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据 ended 为 1(已完成)
     * 传入 uuid project_uuid
     */
    @PostMapping("/endProjectItem")
    public ResponseCode endProjectItem(@RequestBody ProjectItem projectItem, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        ResponseCode responseCode = new ResponseCode();
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        int isUserBelongsToProject = projectService.checkProjectUser(loginId.toString(),projectItem.getProjectUuid()); //验证 当前登录用户是否属于传入的 project id
        if(isUserBelongsToProject < 1) //当前用户不属于项目 则返回错误 401
        {
            responseCode.setCode(401);
            return responseCode;
        }
        responseCode.setCode(projectItemService.endProjectItem(projectItem));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据中的 assignUser 数据
     * 传入 uuid projectUuid assignUserUuid
     */
    @PostMapping("/pickProjectItemUser")
    public ResponseCode pickProjectItemUser(@RequestBody ProjectItem projectItem, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        ResponseCode responseCode = new ResponseCode();
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        int isUserBelongsToProject = projectService.checkProjectUser(loginId.toString(),projectItem.getProjectUuid()); //验证 当前登录用户是否属于传入的 project id
        if(isUserBelongsToProject < 1) //当前用户不属于项目 则返回错误 401
        {
            responseCode.setCode(401);
            return responseCode;
        }
        responseCode.setCode(projectItemService.pickProjectItemUser(projectItem));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 新增一条 project item 数据
     */
    @PostMapping("/addProjectItem")
    public ResponseCode addProjectItem(@RequestBody ProjectItem projectItem, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        ResponseCode responseCode = new ResponseCode();
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        int isUserBelongsToProject = projectService.checkProjectUser(loginId.toString(),projectItem.getProjectUuid()); //验证 当前登录用户是否属于传入的 project id
        if(isUserBelongsToProject < 1) //当前用户不属于项目 则返回错误 401
        {
            responseCode.setCode(401);
            return responseCode;
        }
        projectItem.setCreateUserUuid(loginId.toString()); //设置创建者 uuid
        responseCode.setCode(projectItemService.addProjectItem(projectItem));
        return responseCode;
    }
}
