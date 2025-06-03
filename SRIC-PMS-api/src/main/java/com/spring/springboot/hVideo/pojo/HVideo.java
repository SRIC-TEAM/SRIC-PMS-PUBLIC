package com.spring.springboot.hVideo.pojo;

import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import lombok.Data;

import java.util.List;

/**
 * - - - - - - - - - -
 * video 实体类
 * 用于获取 video 的数据
 * - - - - - - - - - -
 */

@Data
public class HVideo
{
    private int id;
    private String name;
    private String filename;
    private String number;
    private int mosaic;
    private int category;
    private List<HVideoTag> hVideoTagList;
    private String videoCover;
    private Boolean isVideoCoverNotNull;
}
