package com.spring.springboot.notes.controller;

import com.spring.springboot.notes.service.NotesService;
import com.spring.springboot.notes.pojo.Notes;
import com.spring.springboot.notes.pojo.NotesListRequestPojo;
import com.spring.springboot.notes.pojo.NotesListResponsePojo;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * notes manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/notesManage")
public class NotesManageController
{
    @Autowired
    NotesService notesService;

    /**
     * @author SRIC
     *
     * 获取 notes manage 数据列表 分页
     */
    @GetMapping("/getNotesManageList")
    public NotesListResponsePojo getNotesManageList(NotesListRequestPojo notesListRequest)
    {
        NotesListResponsePojo notesListResponse = new NotesListResponsePojo();
        notesListResponse.setNotesList(notesService.getNotesList(notesListRequest));
        notesListResponse.setTotal(notesService.getNotesCount(notesListRequest));
        return notesListResponse;
    }

    /**
     * @author SRIC
     *
     * 修改一条 note 数据
     */
    @PostMapping("/updateNote")
    public ResponseCode updateNote(@RequestBody Notes note)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(notesService.updateNote(note));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 强制删除一条 note 数据
     */
    @PostMapping("/deleteNoteData")
    public ResponseCode deleteNoteData(@RequestBody Notes note)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(notesService.deleteNoteData(note.getUuid()));
        return responseCode;
    }
}
