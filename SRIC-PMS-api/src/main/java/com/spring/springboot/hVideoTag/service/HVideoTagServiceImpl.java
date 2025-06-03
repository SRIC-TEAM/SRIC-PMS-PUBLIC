package com.spring.springboot.hVideoTag.service;

import com.spring.springboot.hVideoTag.mapper.HVideoTagMapper;
import com.spring.springboot.hVideoTag.pojo.AddHVideoTagRequestPojo;
import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import com.spring.springboot.hVideoTag.pojo.HVideoTagManageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * video tag 服务类
 * - - - - - - - - - -
 */

@Service
public class HVideoTagServiceImpl implements HVideoTagService
{
    @Autowired
    HVideoTagMapper hVideoTagMapper;

    /**
     * @author SRIC
     *
     * 获取 video tag 列表
     */
    @Override
    public List<HVideoTag> getHVideoTagList()
    {
        return hVideoTagMapper.getHVideoTagList();
    }

    /**
     * @author SRIC
     *
     * 添加 video tag
     * 先获取一次 tag 避免重复添加
     * 未重复时 添加 tag
     */
    @Override
    public int addHVideoTag(AddHVideoTagRequestPojo addHVideoTagRequest)
    {
        String uuid = hVideoTagMapper.getHVideoTagByVideoIdAndTagId(addHVideoTagRequest); //获取一次看是否有数据
        if(uuid == null) //无数据则返回正常状态码并添加数据
        {
            hVideoTagMapper.addHVideoTag(addHVideoTagRequest);
            return 200;
        }
        else //有数据则返回错误状态码
        {
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 获取 h video tag 列表
     * 并且获取 拥有 tag 的 video 条数
     */
    @Override
    public List<HVideoTagManageData> getHVideoTagManageDataList()
    {
        return hVideoTagMapper.getHVideoTagManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或者修改 h video tag
     */
    @Override
    public int addOrUpdateHVideoTag(HVideoTag hVideoTag)
    {
        if(hVideoTagMapper.getHVideoTagCountByName(hVideoTag) > 0) //若已有重复的名称
        {
            return 400;
        }
        else
        {
            if(hVideoTag.getId() == 0)
            {
                hVideoTagMapper.addHVideoTagData(hVideoTag);
            }
            else
            {
                hVideoTagMapper.updateHVideoTag(hVideoTag);
            }
            return 200;
        }
    }
}
