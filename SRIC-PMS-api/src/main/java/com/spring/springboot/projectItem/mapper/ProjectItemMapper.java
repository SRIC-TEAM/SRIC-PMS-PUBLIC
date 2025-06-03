package com.spring.springboot.projectItem.mapper;

import com.spring.springboot.project.pojo.ProjectUserRelationPojo;
import com.spring.springboot.projectItem.pojo.ProjectItem;
import com.spring.springboot.projectItem.pojo.ProjectItemListRequestPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_project_mapper 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface ProjectItemMapper
{
    /**
     * @author SRIC
     *
     * 获取 project 下的 project item 数据列表 分页
     */
    @Select(value = "SELECT dpi.*, " +
            "cu.username AS createUserName, " +
            "cu.avatar AS createUserAvatar, " +
            "au.username AS assignUserName, " +
            "au.avatar AS assignUserAvatar " +
            "FROM `data_project_item` dpi " +
            "LEFT JOIN data_user cu ON dpi.create_user_uuid = cu.uuid " +
            "LEFT JOIN data_user au ON dpi.assign_user_uuid = au.uuid " +
            "WHERE dpi.project_uuid = #{projectUuid} " +
            "ORDER BY create_time DESC " +
            "LIMIT #{limitBefore}, #{limitSize}")
    List<ProjectItem> getProjectItemList(ProjectItemListRequestPojo projectItemListRequest);

    /**
     * @author SRIC
     *
     * 获取 project 下的 project item 数据列表 总数
     */
    @Select(value = "SELECT COUNT(*) FROM `data_project_item` WHERE project_uuid = #{projectUuid}")
    int getProjectItemCount(ProjectItemListRequestPojo projectItemListRequest);

    /**
     * @author SRIC
     *
     * 更新 project item 数据 ended
     */
    @Update(value = "UPDATE `data_project_item` SET is_ended = #{isEnded} WHERE uuid = #{uuid}")
    int updateProjectItemEnded(ProjectItem projectItem);

    /**
     * @author SRIC
     *
     * 更新 project item 数据 assignUserUuid
     */
    @Update(value = "UPDATE `data_project_item` SET assign_user_uuid = #{assignUserUuid} WHERE uuid = #{uuid}")
    int updateProjectItemAssignUserUuid(ProjectItem projectItem);

    /**
     * @author SRIC
     *
     * 新增一条 project item 数据
     */
    @Insert(value = "INSERT INTO `data_project_item` (uuid, title, text, create_time, project_uuid, create_user_uuid, assign_user_uuid, is_ended) VALUES (UUID(),#{title},#{text},NOW(),#{projectUuid},#{createUserUuid},NULL,0)")
    int insertProjectItem(ProjectItem projectItem);

    /**
     * @author SRIC
     *
     * 更新 project item 数据 title 与 text
     */
    @Update(value = "UPDATE `data_project_item` SET title = #{title}, text = #{text} WHERE uuid = #{uuid}")
    int updateProjectItemText(ProjectItem projectItem);
}
