package com.spring.springboot.hPicture.service;

import com.spring.springboot.hPicture.pojo.HPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HPictureService
{
    List<HPicture> getHPictureRandomList();
    int getHPictureCount();
    int uploadPicture(MultipartFile file);
}
