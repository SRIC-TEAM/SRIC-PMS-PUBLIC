package com.spring.springboot.hComicTag.service;

import com.spring.springboot.hComicTag.mapper.HComicTagMapper;
import com.spring.springboot.hComicTag.pojo.AddHComicTagRequestPojo;
import com.spring.springboot.hComicTag.pojo.HComicTag;
import com.spring.springboot.hComicTag.pojo.HComicTagManageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * comic tag 服务类
 * - - - - - - - - - -
 */

@Service
public class HComicTagServiceImpl implements HComicTagService
{
    @Autowired
    HComicTagMapper hComicTagMapper;

    /**
     * @author SRIC
     *
     * 获取 comic tag 列表
     */
    @Override
    public List<HComicTag> getHComicTagList()
    {
        return hComicTagMapper.getHComicTagList();
    }

    /**
     * @author SRIC
     *
     * 添加 comic tag
     * 先获取一次 tag 避免重复添加
     * 未重复时 添加 tag
     */
    @Override
    public int addHComicTag(AddHComicTagRequestPojo addHComicTagRequest)
    {
        String uuid = hComicTagMapper.getHComicTagByComicIdAndTagId(addHComicTagRequest); //获取一次看是否有数据
        if(uuid == null) //无数据则返回正常状态码并添加数据
        {
            hComicTagMapper.addHComicTag(addHComicTagRequest);
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
     * 获取 h comic tag 列表
     * 并且获取 拥有 tag 的 comic 条数
     */
    @Override
    public List<HComicTagManageData> getHComicTagManageDataList()
    {
        return hComicTagMapper.getHComicTagManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或者修改 h comic tag
     */
    @Override
    public int addOrUpdateHComicTag(HComicTag hComicTag)
    {
        if(hComicTagMapper.getHComicTagCountByName(hComicTag) > 0) //若已有重复的名称
        {
            return 400;
        }
        else
        {
            if(hComicTag.getId() == 0)
            {
                hComicTagMapper.addHComicTagData(hComicTag);
            }
            else
            {
                hComicTagMapper.updateHComicTag(hComicTag);
            }
            return 200;
        }
    }
}
