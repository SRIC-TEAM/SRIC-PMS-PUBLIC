package com.spring.springboot.hPicture.mapper;

import com.spring.springboot.hPicture.pojo.HPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * 对 data_h_picture 数据库的操作层
 * - - - - - - - - - -
 */

@Mapper
public interface HPictureMapper
{
    /**
     * @author SRIC
     *
     * 获取 picture 列表
     */
    @Select(value = "SELECT * FROM `data_h_picture`")
    List<HPicture> getHPictureList();

    /**
     * @author SRIC
     *
     * 获取 picture 列表数量
     */
    @Select(value = "SELECT COUNT(*) FROM `data_h_picture`")
    int getHPictureCount();

    /**
     * @author SRIC
     *
     * 在 data_h_picture 中新增一条数据 (在文件已经成功保存时)
     */
    @Insert(value = "INSERT INTO `data_h_picture` (id, filename) VALUES(null, #{filename})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addPictureMessage(HPicture hPicture);
}
