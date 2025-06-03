package com.spring.springboot.resources.pojo;

import lombok.Data;

/**
 * resources list request 实体类
 * 获取 resources 列表的请求参数
 */

@Data
public class ResourcesListRequestPojo
{
    private String searchInput;
    private String category;
    private int page;
    private int limitBefore;
    private int limitSize;
    private int level;
}
