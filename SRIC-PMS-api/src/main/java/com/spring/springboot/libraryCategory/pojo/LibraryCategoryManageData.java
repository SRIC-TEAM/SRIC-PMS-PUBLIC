package com.spring.springboot.libraryCategory.pojo;

import lombok.Data;

/**
 * - - - - - - - - - -
 * libraryCategory manage 实体类
 * 用于 library 的 category manage 模块 数据传输
 * - - - - - - - - - -
 */

@Data
public class LibraryCategoryManageData
{
    private int id;
    private String name;
    private int libraryNumber;
}
