package com.spring.springboot.projectItem.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.projectItem.pojo.ProjectItem;
import com.spring.springboot.projectItem.service.ProjectItemService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * project item manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/projectItemManage")
public class ProjectItemManageController
{
    @Autowired
    ProjectItemService projectItemService;

    /**
     * @author SRIC
     *
     * 更新 project item 数据 ended
     * 传入 uuid isEnded
     */
    @PostMapping("/endProjectItemManage")
    public ResponseCode endProjectItemManage(@RequestBody ProjectItem projectItem)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(projectItemService.updateProjectItemEnded(projectItem));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 更新 project item 数据中的 assignUser 数据
     * 传入 uuid assignUserUuid
     */
    @PostMapping("/pickProjectItemUserManage")
    public ResponseCode pickProjectItemUserManage(@RequestBody ProjectItem projectItem)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(projectItemService.pickProjectItemUser(projectItem));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 新增一条 project item 数据
     */
    @PostMapping("/addProjectItemManage")
    public ResponseCode addProjectItemManage(@RequestBody ProjectItem projectItem, @RequestHeader(value = "Authorization", required = false) String tokenValue)
    {
        ResponseCode responseCode = new ResponseCode();
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        projectItem.setCreateUserUuid(loginId.toString()); //设置创建者 uuid
        responseCode.setCode(projectItemService.addProjectItem(projectItem));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 修改 project item 数据 title text
     */
    @PostMapping("/updateProjectItemTextManage")
    public ResponseCode updateProjectItemTextManage(@RequestBody ProjectItem projectItem)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(projectItemService.updateProjectItemText(projectItem));
        return responseCode;
    }
}
