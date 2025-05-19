package com.spring.springboot.hVideoTag.controller;

import com.spring.springboot.hVideoTag.pojo.HVideoTag;
import com.spring.springboot.hVideoTag.pojo.HVideoTagManageData;
import com.spring.springboot.hVideoTag.service.HVideoTagService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * h video tag 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/hManage")
public class HVideoTagManageController
{
    @Autowired
    HVideoTagService hVideoTagService;

    /**
     * @author SRIC
     *
     * 获取 h video tag 列表
     * 并且获取 拥有 tag 的 video 条数
     */
    @GetMapping("/getHVideoTagManageDataList")
    public List<HVideoTagManageData> getHVideoTagManageDataList()
    {
        return hVideoTagService.getHVideoTagManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 h video tag
     */
    @PostMapping("/addOrUpdateHVideoTag")
    public ResponseCode addOrUpdateHVideoTag(@RequestBody HVideoTag hVideoTag)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(hVideoTagService.addOrUpdateHVideoTag(hVideoTag));
        return responseCode;
    }
}
