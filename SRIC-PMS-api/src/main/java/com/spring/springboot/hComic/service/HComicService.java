package com.spring.springboot.hComic.service;

import com.spring.springboot.hComic.pojo.HComic;
import com.spring.springboot.hComic.pojo.HComicData;
import com.spring.springboot.hComic.pojo.HComicRequestPojo;

import java.util.List;

public interface HComicService
{
    List<HComic> getHComicList(HComicRequestPojo hComicRequestPojo);
    HComicData getHComicById(int id);
    int getHComicCount(HComicRequestPojo pojo);
}
