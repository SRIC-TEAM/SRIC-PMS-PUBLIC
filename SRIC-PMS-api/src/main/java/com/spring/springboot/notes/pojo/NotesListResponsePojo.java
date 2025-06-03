package com.spring.springboot.notes.pojo;

import com.spring.springboot.notes.pojo.Notes;
import lombok.Data;

import java.util.List;


/**
 * notes list response 实体类
 * 获取 notes 列表的返回数据实体
 */

@Data
public class NotesListResponsePojo
{
    private List<Notes> notesList;
    private int total;
}
