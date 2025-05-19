package com.spring.springboot.project.pojo;

import com.spring.springboot.user.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * - - - - - - - - - -
 * project 实体类
 * 用于 project 的数据传输
 * - - - - - - - - - -
 */

@Data
public class Project
{
    private String uuid;
    private String name;
    private String createTime;
    private String description;
    private String isEnded;
    private List<User> userList;
}
