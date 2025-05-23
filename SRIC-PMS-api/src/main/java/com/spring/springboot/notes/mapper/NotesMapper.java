package com.spring.springboot.notes.mapper;

import com.spring.springboot.notes.pojo.Notes;
import com.spring.springboot.notes.pojo.NotesListByUserRequestPojo;
import com.spring.springboot.notes.pojo.NotesListRequestPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_notes 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface NotesMapper
{
    /**
     * @author SRIC
     *
     * 获取 notes 数据列表 分页
     */
    @Select(value = "SELECT n.*, u.username " +
            "FROM `data_notes` n " +
            "JOIN `data_user` u ON n.user_uuid = u.uuid " +
            "WHERE ( ( n.TITLE LIKE concat('%',#{searchInput},'%') or n.TEXT LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "ORDER BY create_time DESC " +
            "LIMIT #{limitBefore}, #{limitSize}")
    List<Notes> getNotesList(NotesListRequestPojo notesListRequest);

    /**
     * @author SRIC
     *
     * 获取 notes 数据列表数量
     */
    @Select(value = "SELECT COUNT(*) FROM `data_notes` WHERE ( ( TITLE LIKE concat('%',#{searchInput},'%') or TEXT LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )")
    int getNotesCount(NotesListRequestPojo notesListRequest);

    /**
     * @author SRIC
     *
     * 更新一条 notes 数据
     */
    @Update(value = "UPDATE `data_notes` SET title = #{title}, text = #{text} WHERE uuid = #{uuid}")
    int updateNote(Notes note);

    /**
     * @author SRIC
     *
     * 根据 user_uuid 获取 notes 数据 分页
     * 若传入 searchInput 不为空 便进行匹配
     * 根据 create time 降序排列
     */
    @Select(value = "SELECT * FROM `data_notes` WHERE user_uuid = #{userUuid}" +
            "AND is_deleted = 0 " +
            "AND ( ( TITLE LIKE concat('%',#{searchInput},'%') or TEXT LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "ORDER BY create_time DESC " +
            "LIMIT #{limitBefore}, #{limitSize}")
    List<Notes> getNotesListByUserUuid(NotesListByUserRequestPojo notesListByUserRequest);

    /**
     * @author SRIC
     *
     * 根据 user_uuid 获取 notes 数据 条目数量
     * 若传入 searchInput 不为空 便进行匹配
     * 根据 create time 降序排列
     */
    @Select(value = "SELECT COUNT(*) FROM `data_notes` WHERE user_uuid = #{userUuid}" +
            "AND is_deleted = 0 " +
            "AND ( ( TITLE LIKE concat('%',#{searchInput},'%') or TEXT LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "ORDER BY create_time DESC " +
            "LIMIT #{limitBefore}, #{limitSize}")
    int getNotesCountByUserUuid(NotesListByUserRequestPojo notesListByUserRequest);

    /**
     * @author SRIC
     *
     * 新增一条 note 数据
     */
    @Insert(value = "INSERT INTO `data_notes`(uuid, title, text, user_uuid, create_time) VALUES(UUID(),#{title},#{text},#{userUuid},NOW())")
    int addNote(Notes note);

    /**
     * @author SRIC
     *
     * 根据 uuid 获取 note 数据
     */
    @Select(value = "SELECT * FROM `data_notes` WHERE uuid = #{uuid}")
    Notes getNoteByUuid(@Param(value = "uuid")String uuid);

    /**
     * @author SRIC
     *
     * 逻辑删除一条 note 数据
     */
    @Update(value = "UPDATE `data_notes` SET is_deleted = 1 WHERE uuid = #{uuid}")
    int deleteNote(@Param(value = "uuid")String uuid);

    /**
     * @author SRIC
     *
     * 强制删除一条 note 数据
     */
    @Delete(value = "DELETE FROM `data_notes` WHERE uuid = #{uuid}")
    int deleteNoteData(@Param(value = "uuid")String uuid);
}
