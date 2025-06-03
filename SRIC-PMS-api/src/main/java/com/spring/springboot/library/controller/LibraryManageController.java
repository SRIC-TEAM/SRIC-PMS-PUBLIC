package com.spring.springboot.library.controller;

import com.spring.springboot.library.service.LibraryService;
import com.spring.springboot.library.pojo.AddLibraryResponsePojo;
import com.spring.springboot.library.pojo.Library;
import com.spring.springboot.library.pojo.LibraryEditRequestPojo;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * library 控制类
 * 单独的 manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/libraryManage")
public class LibraryManageController
{
    @Autowired
    LibraryService libraryService;

    /**
     * @author SRIC
     *
     * 根据 id 更新 library 内容数据
     */
    @PostMapping("/editLibraryData")
    public ResponseCode editLibraryData(@RequestBody LibraryEditRequestPojo libraryEditRequest)
    {
        ResponseCode responseCode = new ResponseCode();
        if(libraryService.editLibraryData(libraryEditRequest))
        {
            responseCode.setCode(200);
        }
        else
        {
            responseCode.setCode(400);
        }
        return responseCode;
    }

    /**
     * @author SRIC
     *
     * 添加一条 library
     */
    @PostMapping("/addLibrary")
    public AddLibraryResponsePojo addLibrary(@RequestBody Library library)
    {
        return libraryService.addLibrary(library);
    }

    /**
     * @author SRIC
     *
     * 修改 library 信息
     * 修改 title 和 category
     */
    @PostMapping("editLibraryMessage")
    public ResponseCode editLibraryMessage(@RequestBody Library library)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(libraryService.editLibraryMessage(library));
        return responseCode;
    }
}
