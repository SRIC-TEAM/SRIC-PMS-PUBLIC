package com.spring.springboot.user.controller;

import com.spring.springboot.response.ResponseCode;
import com.spring.springboot.user.pojo.UserManageListRequestPojo;
import com.spring.springboot.user.pojo.UserManageListResponsePojo;
import com.spring.springboot.user.service.UserService;
import com.spring.springboot.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * user 控制类
 * 单独的 manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/userManage")
public class UserManageController
{
    @Autowired
    UserService userService;

    /**
     * @author SRIC
     *
     * 获取 user 数据列表
     */
    @GetMapping("/getUserMessageList")
    public UserManageListResponsePojo getUserMessageList(UserManageListRequestPojo userManageListRequest)
    {
        UserManageListResponsePojo userManageListResponse = new UserManageListResponsePojo();
        userManageListResponse.setUserList(userService.getUserMessageList(userManageListRequest));
        userManageListResponse.setTotal(userService.getUserMessageCount());
        return userManageListResponse;
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 用户名
     * 返回状态码 200成功 400失败
     */
    @PostMapping("/updateUsernameByUuid")
    public ResponseCode updateUsernameByUuid(@RequestBody User user)
    {
        ResponseCode responseCode = new ResponseCode();
        // 定义正则表达式，验证用户名是否仅包含数字、字母、下划线和短横线
        String usernamePattern = "^[a-zA-Z0-9_-]+$";
        if(!user.getUsername().matches(usernamePattern)) //用户名含有非法字符
        {
            responseCode.setCode(500);
            return responseCode;
        }
        User userByUsername = userService.getUserByUsername(user.getUsername()); //通过输入的用户名查找是否有用户
        if(userByUsername != null) //用户名已被注册
        {
            responseCode.setCode(400);
            return responseCode;
        }
        boolean isSuccess =  userService.updateUsernameByUuid(user); //进行修改
        if(isSuccess)
        {
            responseCode.setCode(200);
        }
        else
        {
            responseCode.setCode(401);
        }
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 密码
     * 返回状态码 200成功 400失败
     */
    @PostMapping("/updatePasswordByUuid")
    public ResponseCode updatePasswordByUuid(@RequestBody User user)
    {
        boolean isSuccess = userService.updatePasswordByUuid(user);
        ResponseCode responseCode = new ResponseCode();
        if(isSuccess)
        {
            responseCode.setCode(200);
        }
        else
        {
            responseCode.setCode(400);
        }
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 status 权限等级
     * 返回状态码 200成功 400失败
     */
    @PostMapping("/updateStatusByUuid")
    public ResponseCode updateStatusByUuid(@RequestBody User user)
    {
        boolean isSuccess = userService.updateStatusByUuid(user);
        ResponseCode responseCode = new ResponseCode();
        if(isSuccess)
        {
            responseCode.setCode(200);
        }
        else
        {
            responseCode.setCode(400);
        }
        return responseCode;
    }
}
