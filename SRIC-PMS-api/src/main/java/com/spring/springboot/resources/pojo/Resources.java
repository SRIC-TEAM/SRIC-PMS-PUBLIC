package com.spring.springboot.resources.pojo;

import lombok.Data;

/**
 * - - - - - - - - - -
 * resources 实体类
 * 用于 resources 数据传输
 * - - - - - - - - - -
 */

@Data
public class Resources
{
    private String uuid;
    private String name;
    private String filename;
    private int level;
    private String createTime;
    private int category;
    private String categoryName;
    private String type;
    private String size;
}
