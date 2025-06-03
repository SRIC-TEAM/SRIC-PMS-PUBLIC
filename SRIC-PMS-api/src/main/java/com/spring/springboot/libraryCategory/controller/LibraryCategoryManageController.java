package com.spring.springboot.libraryCategory.controller;

import com.spring.springboot.libraryCategory.pojo.LibraryCategoryManageData;
import com.spring.springboot.libraryCategory.service.LibraryCategoryService;
import com.spring.springboot.libraryCategory.pojo.LibraryCategory;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * library category manage 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/libraryManage")
public class LibraryCategoryManageController
{
    @Autowired
    LibraryCategoryService libraryCategoryService;

    /**
     * @author SRIC
     *
     * 获取 library category 列表
     * 并且获取 category 下 library 的条数
     */
    @GetMapping("/getLibraryCategoryManageDataList")
    public List<LibraryCategoryManageData> getLibraryCategoryManageDataList()
    {
        return libraryCategoryService.getLibraryCategoryManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或修改一条 library category
     */
    @PostMapping("/addOrUpdateLibraryCategory")
    public ResponseCode addOrUpdateLibraryCategory(@RequestBody LibraryCategory libraryCategory)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode(libraryCategoryService.addOrUpdateLibraryCategory(libraryCategory));
        return responseCode;
    }
}
