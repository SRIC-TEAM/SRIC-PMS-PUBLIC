package com.spring.springboot.notes.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.spring.springboot.notes.pojo.NotesListByUserRequestPojo;
import com.spring.springboot.notes.pojo.NotesListResponsePojo;
import com.spring.springboot.notes.service.NotesService;
import com.spring.springboot.notes.pojo.Notes;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * notes 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/notes")
public class NotesController
{
    @Autowired
    NotesService notesService;

    /**
     * @author SRIC
     *
     * 根据用户 token 获取 user uuid 并获取对应的 notes 列表
     */
    @GetMapping("/getNotesListByUser")
    public NotesListResponsePojo getNotesListByUser(NotesListByUserRequestPojo notesListByUserRequest, @RequestHeader("Authorization") String tokenValue)
    {
        NotesListResponsePojo notesListResponse = new NotesListResponsePojo();
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        notesListByUserRequest.setUserUuid(loginId.toString()); //将 uuid 放入参数对象中
        notesListResponse.setNotesList(notesService.getNotesListByUserUuid(notesListByUserRequest)); //根据参数获取 notes 列表
        notesListResponse.setTotal(notesService.getNotesCountByUserUuid(notesListByUserRequest)); //根据参数获取符合条件的 notes 条目总数
        return notesListResponse;
    }

    /**
     * @author SRIC
     *
     * 添加一条 note
     */
    @PostMapping("/addNote")
    public ResponseCode addNote(@RequestBody Notes note, @RequestHeader("Authorization") String tokenValue)
    {
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        note.setUserUuid(loginId.toString()); //将 uuid 放入参数对象中
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(notesService.addNote(note));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 逻辑删除一条 note
     */
    @PostMapping("/deleteNote")
    public ResponseCode deleteNote(@RequestBody Notes note, @RequestHeader("Authorization") String tokenValue)
    {
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(notesService.deleteNote(note.getUuid(),loginId.toString()));
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 修改一条 note
     */
    @PostMapping("/editNote")
    public ResponseCode editNote(@RequestBody Notes note, @RequestHeader("Authorization") String tokenValue)
    {
        Object loginId = StpUtil.getLoginIdByToken(tokenValue); //通过 token 获取当前登录的用户 uuid
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(notesService.editNote(note,loginId.toString()));
        return responseCode;
    }
}
