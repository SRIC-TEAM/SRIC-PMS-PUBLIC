package com.spring.springboot.projectItem.pojo;

import lombok.Data;

/**
 * - - - - - - - - - -
 * project item 实体类
 * 用于 project item 的数据传输
 * - - - - - - - - - -
 */

@Data
public class ProjectItem
{
    private String uuid;
    private String title;
    private String text;
    private String createTime;
    private String projectUuid;
    private String createUserUuid;
    private String assignUserUuid;
    private String isEnded;
    private String createUserName;
    private String createUserAvatar;
    private String assignUserName;
    private String assignUserAvatar;
}
