package com.spring.springboot.resources.mapper;

import com.spring.springboot.resources.pojo.Resources;
import com.spring.springboot.resources.pojo.ResourcesListRequestPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_resources 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface ResourcesMapper
{
    /**
     * @author SRIC
     *
     * 获取 resources 数据列表 分页
     * 从 data_resources 中获取数据
     * 若传入 searchInput 不为空 便进行匹配
     * 若传入 category 不为 0 便进行匹配
     * 获取的数据条目为 从 limitBefore 开始 limitSize 条
     * 根据 createtime 倒叙排列
     *
     * 从 category_resources 中匹配获取 categoryName
     */
    @Select(value = "SELECT dr.*, cr.name as categoryName FROM `data_resources` dr " +
            "LEFT JOIN `category_resources` cr ON dr.category = cr.id " +
            "WHERE ( ( dr.name LIKE concat('%',#{searchInput},'%') or dr.filename LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "AND (dr.category = #{category} OR #{category} = '0') " +
            "ORDER BY create_time DESC " +
            "LIMIT #{limitBefore}, #{limitSize}")
    List<Resources> getResourcesList(ResourcesListRequestPojo resourcesListRequest);

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表数量
     */
    @Select(value = "SELECT COUNT(*) FROM `data_resources` " +
            "WHERE ( ( name LIKE concat('%',#{searchInput},'%') or filename LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' ) " +
            "AND (category = #{category} OR #{category} = '0')")
    int getResourcesCount(ResourcesListRequestPojo resourcesListRequest);

    /**
     * @author SRIC
     *
     * 更新 resources 数据信息
     */
    @Update(value = "UPDATE `data_resources` SET name = #{name}, category = #{category}, level = #{level} WHERE UUID = #{uuid}")
    int updateResourcesMessage(Resources resources);

    /**
     * @author SRIC
     *
     * 在 data_resources 中查询是否有重复名称
     */
    @Select(value = "SELECT COUNT(*) FROM `data_resources` WHERE name = #{name} AND uuid != #{uuid}")
    int getResourcesCountByName(Resources resources);

    /**
     * @author SRIC
     *
     * 在 data_resources 中查询名称是否存在
     */
    @Select(value = "SELECT COUNT(*) FROM `data_resources` WHERE name = #{name}")
    int checkResourcesName(@Param(value = "name")String name);

    /**
     * @author SRIC
     *
     * 在 data_resources 中新增一条数据 (在文件已经成功保存时)
     */
    @Insert(value = "INSERT INTO `data_resources` (uuid, name, filename, level, create_time, category, type, size) VALUES(UUID(),#{name},#{filename},#{level},NOW(),#{category},#{type},#{size})")
    int addResourcesMessage(Resources resources);

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表 分页
     * 从 data_resources 中获取数据
     * 若传入 searchInput 不为空 便进行匹配
     * 若传入 category 不为 0 便进行匹配
     * 需要传入的用户权限等级小于资源所需等级
     * 获取的数据条目为 从 limitBefore 开始 limitSize 条
     * 根据 createtime 倒叙排列
     *
     * 从 category_resources 中匹配获取 categoryName
     */
    @Select(value = "SELECT dr.*, cr.name as categoryName FROM `data_resources` dr " +
            "LEFT JOIN `category_resources` cr ON dr.category = cr.id " +
            "WHERE ( ( dr.name LIKE concat('%',#{searchInput},'%') or dr.filename LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "AND (dr.category = #{category} OR #{category} = '0') " +
            "AND (dr.level >= #{level}) " +
            "ORDER BY create_time DESC " +
            "LIMIT #{limitBefore}, #{limitSize}")
    List<Resources> getResourcesListWithPermission(ResourcesListRequestPojo resourcesListRequest);

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表数量
     * 根据用户等级筛选的方法
     */
    @Select(value = "SELECT COUNT(*) FROM `data_resources` " +
            "WHERE ( ( name LIKE concat('%',#{searchInput},'%') or filename LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' ) " +
            "AND (level >= #{level}) " +
            "AND (category = #{category} OR #{category} = '0')")
    int getResourcesCountWithPermission(ResourcesListRequestPojo resourcesListRequest);

    /**
     * @author SRIC
     *
     * 通过 uuid 获取 resources
     */
    @Select(value = "SELECT * FROM `data_resources` WHERE uuid = #{uuid}")
    Resources getResourcesByUuid(Resources resources);
}
