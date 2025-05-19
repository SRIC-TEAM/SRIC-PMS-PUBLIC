package com.spring.springboot.user.mapper;

import com.spring.springboot.user.pojo.SignUpRequestPojo;
import com.spring.springboot.user.pojo.User;
import com.spring.springboot.user.pojo.UserManageListRequestPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 对 data_user 数据库的操作层
 */

@Mapper
public interface UserMapper
{
    /**
     * @author SRIC
     *
     * 获取 user 数据列表
     */
    @Select(value = "SELECT * FROM `data_user`")
    List<User> getUserList();

    /**
     * @author SRIC
     *
     * 获取 user 数据列表 分页
     */
    @Select(value = "SELECT * FROM `data_user` LIMIT #{limitBefore}, #{limitSize}")
    List<User> getUserManageList(UserManageListRequestPojo userManageListRequest);

    /**
     * @author SRIC
     *
     * 获取 user 数据列表总数
     */
    @Select(value = "SELECT COUNT(*) FROM `data_user`")
    int getUserManageCount();

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 username
     */
    @Update(value = "UPDATE `data_user` SET USERNAME = #{username} WHERE uuid = #{uuid}")
    int updateUsernameByUuid(@Param("uuid") String uuid, @Param("username") String username);

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 password
     */
    @Update(value = "UPDATE `data_user` SET PASSWORD = #{password} WHERE uuid = #{uuid}")
    int updatePasswordByUuid(@Param("uuid") String uuid, @Param("password") String password);

    /**
     * @author SRIC
     *
     * 根据 username 获取 user 数据
     */
    @Select(value = "SELECT * FROM `data_user` WHERE USERNAME = #{username}")
    User getUserByUsername(@Param("username") String username);

    /**
     * @author SRIC
     *
     * 根据 uuid 获取 user 数据
     */
    @Select(value = "SELECT * FROM `data_user` WHERE uuid = #{uuid}")
    User getUserByUuid(@Param("uuid") String uuid);

    /**
     * @author SRIC
     *
     * 根据 mail 获取 user 数据
     */
    @Select(value = "SELECT * FROM `data_user` WHERE MAIL = #{mail}")
    User getUserByMail(@Param("mail") String mail);

    /**
     * @author SRIC
     *
     * 添加一条 user 数据
     */
    @Insert(value = "INSERT INTO `data_user` (uuid, UID, username, password, mail, create_time, avatar, mark, status) VALUES(UUID(),null,#{username},#{password},#{mail},NOW(),DEFAULT,DEFAULT,DEFAULT)")
    int insertUser(SignUpRequestPojo signUpRequest);

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 status 权限
     */
    @Update(value = "UPDATE `data_user` SET STATUS = #{status} WHERE uuid = #{uuid}")
    int updateStatusByUuid(User user);

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 username 和 mark
     */
    @Update(value = "UPDATE `data_user` SET USERNAME = #{username}, MARK = #{mark} WHERE uuid = #{uuid}")
    int updateUserProfileByUuid(User user);

    /**
     * @author SRIC
     *
     * 根据 username 检查是否有 user 数据
     * 去除了当前用户这条数据
     */
    @Select(value = "SELECT * FROM `data_user` WHERE USERNAME = #{username} AND uuid != #{uuid}")
    User checkUserByUsername(User user);

    /**
     * @author SRIC
     *
     * 根据 uuid 修改 avatar
     */
    @Update(value = "UPDATE `data_user` SET avatar = #{avatar} WHERE uuid = #{uuid}")
    int updateAvatarByUuid(@Param("uuid") String uuid,@Param("avatar") String avatar);

    @Select(value = "SELECT uuid,username,mail FROM `data_user` WHERE `username` LIKE CONCAT('%', #{keyWord}, '%') OR `mail` LIKE CONCAT('%', #{keyWord}, '%') LIMIT 20")
    List<User> getUserListByKeyWord(@Param("keyWord") String keyWord);
}
