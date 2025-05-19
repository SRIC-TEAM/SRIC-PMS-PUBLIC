package com.spring.springboot.hComicTag.controller;

import com.spring.springboot.hComicTag.service.HComicTagService;
import com.spring.springboot.hComicTag.pojo.AddHComicTagRequestPojo;
import com.spring.springboot.hComicTag.pojo.HComicTag;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * Comic Tag 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/h")
public class HComicTagController
{
    @Autowired
    HComicTagService hComicTagService;

    /**
     * @author SRIC
     *
     * 获取 comic tag 列表
     */
    @GetMapping("/getHComicTagList")
    public List<HComicTag> getHComicTagList()
    {
        return hComicTagService.getHComicTagList();
    }

    /**
     * @author SRIC
     *
     * 添加 comic tag
     */
    @PostMapping("/addHComicTag")
    public ResponseCode addHComicTag(@RequestBody AddHComicTagRequestPojo addHComicTagRequest)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(hComicTagService.addHComicTag(addHComicTagRequest));
        return responseCode;
    }
}
