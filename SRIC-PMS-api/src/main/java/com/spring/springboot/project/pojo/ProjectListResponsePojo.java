package com.spring.springboot.project.pojo;

import lombok.Data;

import java.util.List;

/**
 * project list response 实体类
 * 获取 project request 列表的返回数据实体
 */

@Data
public class ProjectListResponsePojo
{
    private List<Project> projectList;
    private int total;
}
