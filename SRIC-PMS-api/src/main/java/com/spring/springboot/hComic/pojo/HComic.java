package com.spring.springboot.hComic.pojo;

import com.spring.springboot.hComicTag.pojo.HComicTag;
import lombok.Data;

import java.util.List;

/**
 * - - - - - - - - - -
 * H Comic 实体类
 * 用于从数据库获取 H Comic 并且记录 Tag
 * - - - - - - - - - -
 */

@Data
public class HComic
{
    private int id;
    private String name;
    private String filename;
    private int pages;
    private int mosaic;
    private int category;
    private List<HComicTag> hComicTagList;
}
