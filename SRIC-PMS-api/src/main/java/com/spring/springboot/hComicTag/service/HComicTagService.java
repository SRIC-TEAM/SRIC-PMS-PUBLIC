package com.spring.springboot.hComicTag.service;

import com.spring.springboot.hComicTag.pojo.AddHComicTagRequestPojo;
import com.spring.springboot.hComicTag.pojo.HComicTag;
import com.spring.springboot.hComicTag.pojo.HComicTagManageData;

import java.util.List;

public interface HComicTagService
{
    List<HComicTag> getHComicTagList();
    int addHComicTag(AddHComicTagRequestPojo addHComicTagRequest);
    List<HComicTagManageData> getHComicTagManageDataList();
    int addOrUpdateHComicTag(HComicTag hComicTag);
}
