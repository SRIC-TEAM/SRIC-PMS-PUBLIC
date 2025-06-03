package com.spring.springboot.project.mapper;

import com.spring.springboot.project.pojo.Project;
import com.spring.springboot.project.pojo.ProjectListRequestPojo;
import com.spring.springboot.project.pojo.ProjectUserRelationPojo;
import com.spring.springboot.user.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_project 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface ProjectMapper
{
    /**
     * @author SRIC
     *
     * 获取 project 数据列表 分页
     */
    @Select(value = "SELECT * FROM `data_project` LIMIT #{limitBefore}, #{limitSize}")
    List<Project> getProjectList(ProjectListRequestPojo projectListRequest);

    /**
     * @author SRIC
     *
     * 获取 project 数据列表数量
     */
    @Select(value = "SELECT COUNT(*) FROM `data_project`")
    int getProjectCount();

    /**
     * @author SRIC
     *
     * 添加一条 project
     */
    @Insert(value = "INSERT INTO `data_project` (uuid, name, create_time, description, is_ended) VALUES(UUID(),#{name},NOW(),#{description},DEFAULT)")
    int insertProject(Project project);

    /**
     * @author SRIC
     *
     * 修改一条 project
     */
    @Update(value = "UPDATE `data_project` SET name = #{name}, description = #{description}, is_ended = #{isEnded} WHERE uuid = #{uuid}")
    int updateProject(Project project);

    /**
     * @author SRIC
     *
     * 在 data_project 中查询是否有重复名称
     */
    @Select(value = "SELECT COUNT(*) FROM `data_project` WHERE name = #{name} AND uuid != #{uuid}")
    int getProjectCountByName(Project project);

    /**
     * @author SRIC
     *
     * 获取 project 数据列表 分页
     * 使用嵌套查询 获取 user 列表
     */
    @Select(value = "SELECT * FROM data_project LIMIT #{limitBefore}, #{limitSize}")
    @Results({
            @Result(property = "uuid", column = "uuid"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "description", column = "description"),
            @Result(property = "isEnded", column = "is_ended"),
            @Result(property = "userList", column = "uuid",
                    many = @Many(select = "getUsersByProjectUuid"))
    })
    List<Project> getProjectListWithUserList(ProjectListRequestPojo projectListRequest);

    /**
     * @author SRIC
     *
     * 被嵌套的查询
     * 根据 project uuid 查询 relation_project_user 获取相应的 user 信息
     */
    @Select({
            "SELECT u.uuid, u.username, u.avatar FROM relation_project_user r ",
            "JOIN data_user u ON r.user_uuid = u.uuid ",
            "WHERE r.project_uuid = #{projectUuid}"
    })
    List<User> getUsersByProjectUuid(String projectUuid);

    /**
     * @author SRIC
     *
     * 移除对应的 relation
     * 匹配 project_uuid 和 user_uuid
     */
    @Delete(value = "DELETE FROM `relation_project_user` WHERE user_uuid = #{userUuid} AND project_uuid = #{projectUuid}")
    int deleteProjectUserRelation(ProjectUserRelationPojo projectUserRelation);

    /**
     * @author SRIC
     *
     * 检查 relation
     * 检查相应的 project 是否已有 user
     */
    @Select(value = "SELECT COUNT(*) FROM `relation_project_user` WHERE user_uuid = #{userUuid} AND project_uuid = #{projectUuid}")
    int getRelationByProjectAndUser(ProjectUserRelationPojo projectUserRelation);

    /**
     * @author SRIC
     *
     * 添加对应的 relation
     * 添加 project_uuid 和 user_uuid
     */
    @Insert(value = "INSERT INTO `relation_project_user` (uuid, user_uuid, project_uuid) VALUES (UUID(),#{userUuid},#{projectUuid})")
    int insertProjectUserRelation(ProjectUserRelationPojo projectUserRelation);

    /**
     * @author SRIC
     *
     * 获取 project 数据列表 分页
     * 使用嵌套查询 获取 user 列表
     * 根据传入的 userUuid 匹配 user 所加入的 project
     */
    @Select(value = "SELECT * FROM data_project " +
            "WHERE uuid IN (" +
            "    SELECT DISTINCT r.project_uuid FROM relation_project_user r " +
            "    WHERE r.user_uuid = #{userUuid}" +
            ") " +
            "LIMIT #{limitBefore}, #{limitSize}")
    @Results({
            @Result(property = "uuid", column = "uuid"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "description", column = "description"),
            @Result(property = "isEnded", column = "is_ended"),
            @Result(property = "userList", column = "uuid",
                    many = @Many(select = "getUsersByProjectUuid"))
    })
    List<Project> getProjectListWithUserListByUser(ProjectListRequestPojo projectListRequest);

    /**
     * @author SRIC
     *
     * 获取 project 数据
     * 根据 project uuid 获取一条数据
     * 及对应的 用户列表
     */
    @Select(value = "SELECT * FROM data_project WHERE uuid = #{uuid}")
    @Results({
            @Result(property = "uuid", column = "uuid"),
            @Result(property = "name", column = "name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "description", column = "description"),
            @Result(property = "isEnded", column = "is_ended"),
            @Result(property = "userList", column = "uuid",
                    many = @Many(select = "getUsersByProjectUuid"))
    })
    Project getProjectByUuidWithUserList(@Param(value = "uuid")String uuid);
}
