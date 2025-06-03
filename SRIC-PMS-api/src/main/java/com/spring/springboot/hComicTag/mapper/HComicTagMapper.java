package com.spring.springboot.hComicTag.mapper;

import com.spring.springboot.hComicTag.pojo.AddHComicTagRequestPojo;
import com.spring.springboot.hComicTag.pojo.HComicTag;
import com.spring.springboot.hComicTag.pojo.HComicTagManageData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_h_comic_tags 数据库的操作层
 * 对 relation_h_comic_tag 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface HComicTagMapper
{
    /**
     * @author SRIC
     *
     * 通过 comic id 获取 对应的多条 tag
     * 从 relation_h_comic_tag 中 获取对应的 tag id 列表 并且查询到 tag 列表
     */
    @Select(value = "SELECT tags.* " +
            "FROM `relation_h_comic_tag` AS relation " +
            "JOIN `data_h_comic_tags` AS tags ON relation.tagid = tags.id " +
            "WHERE relation.comicid = #{comicId}")
    List<HComicTag> getHComicTagsByComicId(@Param(value = "comicId")int comicId);

    /**
     * @author SRIC
     *
     * 获取 comic tag 列表
     */
    @Select(value = "SELECT * FROM `data_h_comic_tags`")
    List<HComicTag> getHComicTagList();

    /**
     * @author SRIC
     *
     * 通过 comic id 和 tag id 获取 tag
     */
    @Select(value = "SELECT UUID FROM `relation_h_comic_tag` WHERE COMICID = #{comicId} AND TAGID = #{tagId}")
    String getHComicTagByComicIdAndTagId(AddHComicTagRequestPojo addHComicTagRequest);

    /**
     * @author SRIC
     *
     * 将 tag 添加给 comic ( 添加一条数据在 relation 中 )
     */
    @Insert(value = "INSERT INTO `relation_h_comic_tag` values (UUID(), #{tagId}, #{comicId})")
    void addHComicTag(AddHComicTagRequestPojo addHComicTagRequest);

    /**
     * @author SRIC
     *
     * 从 data_h_comic_tags 中获取数据
     * 并且查询 拥有 tag 的 comic 的条数
     */
    @Select(value = "SELECT dhct.id, dhct.name, " +
            "(SELECT COUNT(*) FROM `relation_h_comic_tag` rhct WHERE rhct.tagid = dhct.id) AS number " +
            "FROM `data_h_comic_tags` dhct")
    List<HComicTagManageData> getHComicTagManageDataList();

    /**
     * @author SRIC
     *
     * 为 data_h_comic_tags 添加数据
     */
    @Insert(value = "INSERT INTO `data_h_comic_tags` values (null, #{name})")
    void addHComicTagData(HComicTag hComicTag);

    /**
     * @author SRIC
     *
     * 在 data_h_comic_tags 中修改数据
     */
    @Update(value = "UPDATE `data_h_comic_tags` SET NAME = #{name} WHERE ID = #{id}")
    void updateHComicTag(HComicTag hComicTag);

    /**
     * @author SRIC
     *
     * 在 data_h_comic_tags 中查询是否有重复名称
     */
    @Select(value = "SELECT COUNT(*) FROM `data_h_comic_tags` WHERE NAME = #{name}")
    int getHComicTagCountByName(HComicTag hComicTag);
}
