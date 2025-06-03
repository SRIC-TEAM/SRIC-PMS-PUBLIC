package com.spring.springboot.hComicTag.pojo;

import lombok.Data;

/**
 * - - - - - - - - - -
 * h comic tag manage 实体类
 * 用于 h comic 的 tag manage 模块 数据传输
 * - - - - - - - - - -
 */

@Data
public class HComicTagManageData
{
    private int id;
    private String name;
    private int number;
}
