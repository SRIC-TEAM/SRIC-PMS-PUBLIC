package com.spring.springboot.hVideoTag.service;

import com.spring.springboot.hVideoTag.pojo.AddHVideoTagRequestPojo;
import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import com.spring.springboot.hVideoTag.pojo.HVideoTagManageData;

import java.util.List;

public interface HVideoTagService
{
    List<HVideoTag> getHVideoTagList();
    int addHVideoTag(AddHVideoTagRequestPojo addHVideoTagRequest);
    List<HVideoTagManageData> getHVideoTagManageDataList();
    int addOrUpdateHVideoTag(HVideoTag hVideoTag);
}
