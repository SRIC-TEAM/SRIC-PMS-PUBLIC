package com.spring.springboot.resourcesCategory.mapper;

import com.spring.springboot.resourcesCategory.pojo.ResourcesCategory;
import com.spring.springboot.resourcesCategory.pojo.ResourcesCategoryManageData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 category_resources 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface ResourcesCategoryMapper
{
    /**
     * @author SRIC
     *
     * 从 category_resources 中获取数据
     */
    @Select(value = "SELECT * FROM `category_resources`")
    List<ResourcesCategory> getResourcesCategoryList();

    /**
     * @author SRIC
     *
     * 从 category_resources 中获取数据
     * 并且查询 category 下 resources 的条数
     */
    @Select(value = "SELECT cr.id, cr.name, " +
            "(SELECT COUNT(*) FROM data_resources dr WHERE dr.category = cr.id) AS resourcesNumber " +
            "FROM category_resources cr")
    List<ResourcesCategoryManageData> getResourcesCategoryManageDataList();

    /**
     * @author SRIC
     *
     * 为 category_resources 添加数据
     */
    @Insert(value = "INSERT INTO `category_resources` values (null, #{name})")
    void addResourcesCategory(ResourcesCategory resourcesCategory);

    /**
     * @author SRIC
     *
     * 在 category_resources 中修改数据
     */
    @Update(value = "UPDATE `category_resources` SET NAME = #{name} WHERE ID = #{id}")
    void updateResourcesCategory(ResourcesCategory resourcesCategory);

    /**
     * @author SRIC
     *
     * 在 category_resources 中查询是否有重复名称
     */
    @Select(value = "SELECT COUNT(*) FROM `category_resources` WHERE NAME = #{name}")
    int getResourcesCategoryCountByName(ResourcesCategory resourcesCategory);
}
