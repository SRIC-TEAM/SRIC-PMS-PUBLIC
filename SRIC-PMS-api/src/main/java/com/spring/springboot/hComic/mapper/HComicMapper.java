package com.spring.springboot.hComic.mapper;

import com.spring.springboot.hComic.pojo.HComicRequestPojo;
import com.spring.springboot.hComic.pojo.HComic;
import com.spring.springboot.hComicTag.pojo.HComicTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_h_comic 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface
HComicMapper
{
    /**
     * @author SRIC
     *
     * 从 data_h_comic 中获取数据
     * 若传入 searchInput 不为空 便进行匹配
     * 若传入 mosaicSelect 不为 0 便进行匹配
     * 若传入 categorySelect 不为 0 便进行匹配
     * 获取的数据条目为 从 limitBefore 开始 limitSize 条
     *
     * 嵌套查询 获取 tag list
     */
    @Select(value = "SELECT * FROM `data_h_comic` WHERE " +
            "( NAME LIKE concat('%',#{searchInput},'%') OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "AND ( MOSAIC = #{mosaicSelect} OR #{mosaicSelect} = '0' )" +
            "AND ( CATEGORY = #{categorySelect} OR #{categorySelect} = '0' )" +
            "LIMIT #{limitBefore} , #{limitSize}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "filename", column = "filename"),
            @Result(property = "pages", column = "pages"),
            @Result(property = "mosaic", column = "mosaic"),
            @Result(property = "category", column = "category"),
            @Result(property = "hComicTagList", column = "id",
                    many = @Many(select = "getTagsByComicId"))
    })
    List<HComic> getHComicList(HComicRequestPojo hComicRequest);

    /**
     * @author SRIC
     *
     * 被嵌套的查询
     * 通过 comic id 查询 data_h_comic_tag 表 获取 tag 信息
     */
    @Select({
            "SELECT t.id, t.name FROM relation_h_comic_tag r ",
            "JOIN data_h_comic_tags t ON r.tagid = t.id ",
            "WHERE r.comicid = #{comicId}"
    })
    List<HComicTag> getTagsByComicId(int comicId);

    /**
     * @author SRIC
     *
     * 通过 id 获取 comic
     */
    @Select(value = "SELECT * FROM `data_h_comic` WHERE ID = #{id}")
    HComic getHComicById(@Param(value = "id")int id);

    /**
     * @author SRIC
     *
     * 从 data_h_comic 中获取数据条数
     * 若传入 searchInput 不为空 便进行匹配
     * 若传入 mosaicSelect 不为 0 便进行匹配
     * 若传入 categorySelect 不为 0 便进行匹配
     */
    @Select(value = "SELECT COUNT(*) FROM `data_h_comic` WHERE " +
            "( NAME LIKE concat('%',#{searchInput},'%') OR #{searchInput} IS NULL OR #{searchInput} = '' )" +
            "AND ( MOSAIC = #{mosaicSelect} OR #{mosaicSelect} = '0' )" +
            "AND ( CATEGORY = #{categorySelect} OR #{categorySelect} = '0' )")
    int getHComicCount(HComicRequestPojo hComicRequest);
}
