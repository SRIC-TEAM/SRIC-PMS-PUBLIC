package com.spring.springboot.resourcesCategory.pojo;

import lombok.Data;

/**
 * - - - - - - - - - -
 * resourcesCategory manage 实体类
 * 用于 resources 的 category manage 模块 数据传输
 * - - - - - - - - - -
 */

@Data
public class ResourcesCategoryManageData
{
    private int id;
    private String name;
    private int resourcesNumber;
}
