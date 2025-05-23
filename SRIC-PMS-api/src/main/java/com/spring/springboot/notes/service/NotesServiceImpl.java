package com.spring.springboot.notes.service;

import com.spring.springboot.notes.mapper.NotesMapper;
import com.spring.springboot.notes.pojo.Notes;
import com.spring.springboot.notes.pojo.NotesListByUserRequestPojo;
import com.spring.springboot.notes.pojo.NotesListRequestPojo;
import com.spring.springboot.notes.service.NotesService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * notes 服务类
 * - - - - - - - - - -
 */

@Service
public class NotesServiceImpl implements NotesService
{
    @Autowired
    NotesMapper notesMapper;

    /**
     * @author SRIC
     *
     * 获取 notes 数据列表 分页
     */
    @Override
    public List<Notes> getNotesList(NotesListRequestPojo notesListRequest)
    {
        notesListRequest.setLimitSize(20); //一页获取20条数据
        notesListRequest.setLimitBefore((notesListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return notesMapper.getNotesList(notesListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 notes 数据列表数量
     */
    @Override
    public int getNotesCount(NotesListRequestPojo notesListRequest)
    {
        return notesMapper.getNotesCount(notesListRequest);
    }

    /**
     * @author SRIC
     *
     * 修改一条 note 标题或内容
     * 管理页面进行修改
     */
    @Override
    public int updateNote(Notes note)
    {
        if(notesMapper.updateNote(note) != 0)
        {
            return 200;
        }
        else
        {
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 根据用户 uuid 获取 notes 数据列表 分页
     */
    @Override
    public List<Notes> getNotesListByUserUuid(NotesListByUserRequestPojo notesListByUserRequest)
    {
        notesListByUserRequest.setLimitSize(20); //一页获取20条数据
        notesListByUserRequest.setLimitBefore((notesListByUserRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return notesMapper.getNotesListByUserUuid(notesListByUserRequest);
    }

    /**
     * @author SRIC
     *
     * 根据用户 uuid 获取 notes 数据条目数量
     */
    @Override
    public int getNotesCountByUserUuid(NotesListByUserRequestPojo notesListByUserRequest)
    {
        return notesMapper.getNotesCountByUserUuid(notesListByUserRequest);
    }

    /**
     * @author SRIC
     *
     * 添加一条 note
     */
    @Override
    public int addNote(Notes note)
    {
        if(notesMapper.addNote(note) == 1)
        {
            return 200;
        }
        else
        {
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 逻辑删除一条 note
     */
    @Override
    public int deleteNote(String uuid,String userUuid)
    {
        Notes noteNow = notesMapper.getNoteByUuid(uuid); //根据传入需要删除的 note 的 uuid 获取 note 数据（拿到 user uuid）
        if(noteNow.getUserUuid().equals(userUuid)) //若当前登录用户的 uuid 与 需删除的 note 的 user uuid 相同
        {
            if(notesMapper.deleteNote(uuid) > 0)
            {
                return 200; //成功
            }
            else
            {
                return 400; //失败
            }
        }
        else
        {
            return 401; //删除了非当前登录用户的笔记
        }
    }

    /**
     * @author SRIC
     *
     * 强制删除一条 note
     */
    @Override
    public int deleteNoteData(String uuid)
    {
        if(notesMapper.deleteNoteData(uuid) > 0)
        {
            return 200;
        }
        else
        {
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 编辑一条 note
     * 用户进行编辑
     */
    @Override
    public int editNote(Notes note,String userUuid)
    {
        Notes noteNow = notesMapper.getNoteByUuid(note.getUuid()); //根据传入需要删除的 note 的 uuid 获取 note 数据（拿到 user uuid）
        if(noteNow.getUserUuid().equals(userUuid)) //若当前登录用户的 uuid 与 需删除的 note 的 user uuid 相同
        {
            if(notesMapper.updateNote(note) != 0)
            {
                return 200; //成功
            }
            else
            {
                return 400; //失败
            }
        }
        else
        {
            return 401; //修改了非当前登录用户的笔记
        }
    }
}
