package com.spring.springboot.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.permissionRequest.service.PermissionRequestService;
import com.spring.springboot.tools.GetTime;
import com.spring.springboot.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求拦截器
 */

@Component
public class intercept implements HandlerInterceptor
{
    @Autowired
    UserService userService;

    @Autowired
    PermissionRequestService permissionRequestService;

    /**
     * @author SRIC
     *
     * 拦截每一次请求
     * 记录请求 url
     * 判断 登录 状态和 token 状态
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception
    {
        if(request.getMethod().equals("OPTIONS")) //对预检请求放行
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String url = request.getRequestURI(); //请求 url
        String[] rulParts = url.split("/"); //请求模块

        if(rulParts.length < 2) //对于无附带的请求 放行 (axios请求 length 应该为 4)
        {
            return true;
        }

        if(!rulParts[1].equals("api")) //若请求不为 api 开头 则不进行下面的判断 放行
        {
            return true;
        }

        if(rulParts[2].equals("file")) //对静态文件访问放行
        {
            return true;
        }

        LogPojo log = new LogPojo();
        log.setCurrentTime(new GetTime().getCurrentTime()); //获取时间
        log.setClientIp(getClientIp(request)); //获取IP
        log.setRequestUrl(url); //获取请求url
        log.setUrlModule(rulParts[2]); //获取权限模块
        if(log.getUrlModule().equals("file")) //对静态文件访问放行
        {
            return true;
        }
        String tokenValue = request.getHeader("Authorization");
        log.setTokenData(tokenValue);
        if (tokenValue == null) //未登录 无token
        {
            log.setState("未登录");
            log.setLoginId("NULL");
            log.setUserPermissionLevel(10); //未登录时 系统权限为最低 10
        }
        else //登录过 有token
        {
            Object loginId = StpUtil.getLoginIdByToken(tokenValue);
            if(loginId == null) //token过期
            {
                log.setState("token过期");
                log.setLoginId("NULL");
                log.setUserPermissionLevel(10); //登录过期时 系统权限为最低 10
            }
            else //正常的登录状态
            {
                log.setState("已登录");
                log.setLoginId(loginId.toString());
                log.setUserPermissionLevel(userService.getUserStatusByUuid(loginId.toString()));
            }
        }
        log.setInterfacePermissionLevel(permissionRequestService.getInterfacePermissionLevelByRequestMapping(log.getUrlModule())); //根据权限模块获取接口权限等级
        log.setPermissionPassed(log.getUserPermissionLevel() <= log.getInterfacePermissionLevel()); //鉴权是否通过
        showLogs(log);
        if(!log.isPermissionPassed()) //未通过
        {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 设置状态码为 403
            return false;
        }
        return true;
    }

    /**
     * 获取客户端 IP 地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For"); // 从代理服务器获取IP
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr(); // 最后从request中获取IP
        }
        return ip;
    }

    /**
     * 显示请求 log 信息
     */
    private void showLogs(LogPojo log)
    {
        System.out.println("\n"
                + "- - - - -  Q w Q  - - - - -"
                + "\n"
                + "请求时间 : " + log.getCurrentTime()
                + "\n"
                + "请求IP : " + log.getClientIp()
                + "\n"
                + "请求URL : " + log.getRequestUrl()
                + "\n"
                + "权限模块 : " + log.getUrlModule()
                + "\n"
                + "状态 : " + log.getState()
                + "\n"
                + "token内容 : " + log.getTokenData()
                + "\n"
                + "登录ID : " + log.getLoginId()
                + "\n"
                + "用户权限 : " + log.getUserPermissionLevel()
                + "\n"
                + "接口权限 : " + log.getInterfacePermissionLevel()
                + "\n"
                + "鉴权通过情况 : " + log.isPermissionPassed()
        );
    }
}
