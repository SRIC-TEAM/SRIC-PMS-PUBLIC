package com.spring.springboot.hComicTag.controller;

import com.spring.springboot.hComicTag.pojo.HComicTag;
import com.spring.springboot.hComicTag.pojo.HComicTagManageData;
import com.spring.springboot.hComicTag.service.HComicTagService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * h comic tag 管理 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/hManage")
public class HComicTagManageController
{
    @Autowired
    HComicTagService hComicTagService;

    /**
     * @author SRIC
     *
     * 获取 h comic tag 列表
     * 并且获取 拥有 tag 的 comic 条数
     */
    @GetMapping("/getHComicTagManageDataList")
    public List<HComicTagManageData> getHComicTagManageDataList()
    {
        return hComicTagService.getHComicTagManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 h comic tag
     */
    @PostMapping("/addOrUpdateHComicTag")
    public ResponseCode addOrUpdateHComicTag(@RequestBody HComicTag hComicTag)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(hComicTagService.addOrUpdateHComicTag(hComicTag));
        return responseCode;
    }
}
