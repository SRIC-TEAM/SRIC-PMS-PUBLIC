package com.spring.springboot.library.pojo;

import lombok.Data;

/**
 * library request 实体类
 * 获取 library 列表的请求参数
 */

@Data
public class LibraryListRequestPojo
{
    private String searchInput;
    private String category;
    private int page;
    private int limitBefore;
    private int limitSize;
}
