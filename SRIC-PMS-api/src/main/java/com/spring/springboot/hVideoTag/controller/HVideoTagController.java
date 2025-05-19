package com.spring.springboot.hVideoTag.controller;

import com.spring.springboot.hVideoTag.service.HVideoTagService;
import com.spring.springboot.hVideoTag.pojo.AddHVideoTagRequestPojo;
import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

/**
 * - - - - - - - - - -
 * video tag 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/h")
public class HVideoTagController
{
    @Autowired
    HVideoTagService hVideoTagService;

    /**
     * @author SRIC
     *
     * 获取 video tag 列表
     */
    @GetMapping("/getHVideoTagList")
    public List<HVideoTag> getHVideoTagList()
    {
        return hVideoTagService.getHVideoTagList();
    }

    /**
     * @author SRIC
     *
     * 添加 video tag
     */
    @PostMapping("/addHVideoTag")
    public ResponseCode addHVideoTag(@RequestBody AddHVideoTagRequestPojo addHVideoTagRequest)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(hVideoTagService.addHVideoTag(addHVideoTagRequest));
        return responseCode;
    }
}
