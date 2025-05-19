package com.spring.springboot.projectItem.pojo;

import lombok.Data;

import java.util.List;

/**
 * project item list response 实体类
 * 获取 project item request 列表的返回数据实体
 */

@Data
public class ProjectItemListResponsePojo
{
    private List<ProjectItem> projectItemList;
    private int total;
}
