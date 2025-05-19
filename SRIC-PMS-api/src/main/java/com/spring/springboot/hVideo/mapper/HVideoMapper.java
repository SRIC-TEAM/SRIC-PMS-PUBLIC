package com.spring.springboot.hVideo.mapper;

import com.spring.springboot.hVideo.pojo.HVideoRequestPojo;
import com.spring.springboot.hVideo.pojo.HVideo;
import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_h_video 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface HVideoMapper
{
    /**
     * @author SRIC
     *
     * 从 data_h_video 中获取数据
     * 若传入 searchInput 不为空 便进行匹配
     * 若传入 mosaicSelect 不为 0 便进行匹配
     * 若传入 categorySelect 不为 0 便进行匹配
     * 获取的数据条目为 从 limitBefore 开始 limitSize 条
     *
     * 嵌套查询 获取 tag list
     */
    @Select(value = "SELECT * FROM `data_h_video` WHERE " +
            "( ( NAME LIKE concat('%',#{searchInput},'%') or NUMBER LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "AND ( MOSAIC = #{mosaicSelect} OR #{mosaicSelect} = '0' )" +
            "AND ( CATEGORY = #{categorySelect} OR #{categorySelect} = '0' )" +
            "LIMIT #{limitBefore} , #{limitSize}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "filename", column = "filename"),
            @Result(property = "number", column = "number"),
            @Result(property = "mosaic", column = "mosaic"),
            @Result(property = "category", column = "category"),
            @Result(property = "hVideoTagList", column = "id",
                    many = @Many(select = "getTagsByVideoId"))
    })
    List<HVideo> getHVideoList(HVideoRequestPojo pojo);

    /**
     * @author SRIC
     *
     * 被嵌套的查询
     * 通过 video id 查询 data_h_video_tag 表 获取 tag 信息
     */
    @Select({
            "SELECT t.id, t.name FROM relation_h_video_tag r ",
            "JOIN data_h_video_tags t ON r.tagid = t.id ",
            "WHERE r.videoid = #{videoid}"
    })
    List<HVideoTag> getTagsByVideoId(int videoid);

    /**
     * @author SRIC
     *
     * 通过 id 获取 video
     */
    @Select(value = "SELECT * FROM `data_h_video` WHERE ID = #{id}")
    HVideo getHVideoById(@Param(value = "id")int id);

    /**
     * @author SRIC
     *
     * 从 data_h_video 中获取数据条数
     * 若传入 searchInput 不为空 便进行匹配
     * 若传入 mosaicSelect 不为 0 便进行匹配
     * 若传入 categorySelect 不为 0 便进行匹配
     */
    @Select(value = "SELECT COUNT(*) FROM `data_h_video` WHERE " +
            "( ( NAME LIKE concat('%',#{searchInput},'%') or NUMBER LIKE concat('%',#{searchInput},'%') ) OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "AND ( MOSAIC = #{mosaicSelect} OR #{mosaicSelect} = '0' )" +
            "AND ( CATEGORY = #{categorySelect} OR #{categorySelect} = '0' )")
    int getHVideoCount(HVideoRequestPojo pojo);
}
