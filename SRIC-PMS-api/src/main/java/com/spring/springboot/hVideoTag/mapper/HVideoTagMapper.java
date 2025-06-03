package com.spring.springboot.hVideoTag.mapper;

import com.spring.springboot.hVideoTag.pojo.AddHVideoTagRequestPojo;
import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import com.spring.springboot.hVideoTag.pojo.HVideoTagManageData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_h_video_tags 数据库的操作层
 * 对 relation_h_video_tag 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface HVideoTagMapper
{

    /**
     * @author SRIC
     *
     * 通过 video id 获取 对应的多条 tag
     * 从 relation_h_video_tag 中 获取对应的 tag id 列表 并且查询到 tag 列表
     */
    @Select(value = "SELECT tags.* " +
            "FROM `relation_h_video_tag` AS relation " +
            "JOIN `data_h_video_tags` AS tags ON relation.tagid = tags.id " +
            "WHERE relation.videoid = #{videoId}")
    List<HVideoTag> getHVideoTagsByVideoId(@Param(value = "videoId")int videoId);

    /**
     * @author SRIC
     *
     * 获取 video tag 列表
     */
    @Select(value = "SELECT * FROM `data_h_video_tags`")
    List<HVideoTag> getHVideoTagList();

    /**
     * @author SRIC
     *
     * 通过 video id 和 tag id 获取 tag
     */
    @Select(value = "SELECT UUID FROM `relation_h_video_tag` WHERE VIDEOID = #{videoId} AND TAGID = #{tagId}")
    String getHVideoTagByVideoIdAndTagId(AddHVideoTagRequestPojo addHVideoTagRequest);

    /**
     * @author SRIC
     *
     * 将 tag 添加给 video ( 添加一条数据在 relation 中 )
     */
    @Insert(value = "INSERT INTO `relation_h_video_tag` values (UUID(), #{tagId}, #{videoId})")
    void addHVideoTag(AddHVideoTagRequestPojo addHVideoTagRequest);

    /**
     * @author SRIC
     *
     * 从 data_h_video_tags 中获取数据
     * 并且查询 拥有 tag 的 video 的条数
     */
    @Select(value = "SELECT dhvt.id, dhvt.name, " +
            "(SELECT COUNT(*) FROM `relation_h_video_tag` rhvt WHERE rhvt.tagid = dhvt.id) AS number " +
            "FROM `data_h_video_tags` dhvt")
    List<HVideoTagManageData> getHVideoTagManageDataList();

    /**
     * @author SRIC
     *
     * 为 data_h_video_tags 添加数据
     */
    @Insert(value = "INSERT INTO `data_h_video_tags` values (null, #{name})")
    void addHVideoTagData(HVideoTag hVideoTag);

    /**
     * @author SRIC
     *
     * 在 data_h_video_tags 中修改数据
     */
    @Update(value = "UPDATE `data_h_video_tags` SET NAME = #{name} WHERE ID = #{id}")
    void updateHVideoTag(HVideoTag hVideoTag);

    /**
     * @author SRIC
     *
     * 在 data_h_video_tags 中查询是否有重复名称
     */
    @Select(value = "SELECT COUNT(*) FROM `data_h_video_tags` WHERE NAME = #{name}")
    int getHVideoTagCountByName(HVideoTag hVideoTag);
}
