package com.spring.springboot.projectItem.pojo;

import lombok.Data;

/**
 * project item list request 实体类
 * 获取 project item request 列表的请求参数
 */

@Data
public class ProjectItemListRequestPojo
{
    private String projectUuid;
    private int page;
    private int limitBefore;
    private int limitSize;
}
