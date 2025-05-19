package com.spring.springboot.hVideo.service;

import com.spring.springboot.hVideo.pojo.HVideo;
import com.spring.springboot.hVideo.pojo.HVideoRequestPojo;

import java.util.List;

public interface HVideoService
{
    List<HVideo> getHVideoList(HVideoRequestPojo hVideoRequest);
    HVideo getHVideoById(int id);
    int getHVideoCount(HVideoRequestPojo pojo);
}
