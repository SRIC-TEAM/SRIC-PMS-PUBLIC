package com.spring.springboot.hPicture.controller;

import com.spring.springboot.hPicture.service.HPictureService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * - - - - - - - - - -
 * h picture 管理 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/hManage")
public class HPictureManageController
{
    @Autowired
    HPictureService hPictureService;

    /**
     * @author SRIC
     *
     * 获取 picture 当前编号 需要获取数据库中图片数量 +1 作为当前编号
     */
    @GetMapping("/getHPictureNumber")
    public int getHPictureNumber()
    {
        return hPictureService.getHPictureCount() + 1;
    }

    /**
     * @author SRIC
     *
     * 上传一条 picture 文件
     */
    @PostMapping("/uploadPicture")
    public ResponseCode uploadPicture(@RequestParam("file") MultipartFile file)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(hPictureService.uploadPicture(file));
        return responseCode;
    }
}
