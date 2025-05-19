package com.spring.springboot.project.pojo;

import lombok.Data;

/**
 * project list request 实体类
 * 获取 project request 列表的请求参数
 */

@Data
public class ProjectListRequestPojo
{
    private String userUuid; //需要通过 user uuid 获取当前用户加入的项目
    private int page;
    private int limitBefore;
    private int limitSize;
}
