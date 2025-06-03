package com.spring.springboot.resources.pojo;

import com.spring.springboot.resources.pojo.Resources;
import lombok.Data;

import java.util.List;

/**
 * resources list response 实体类
 * 获取 resources 列表的返回数据实体
 */

@Data
public class ResourcesListResponsePojo
{
    private List<Resources> resourcesList;
    private int total;
}
