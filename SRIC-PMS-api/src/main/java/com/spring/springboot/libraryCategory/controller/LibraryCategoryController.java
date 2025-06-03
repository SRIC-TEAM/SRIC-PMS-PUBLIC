package com.spring.springboot.libraryCategory.controller;

import com.spring.springboot.libraryCategory.service.LibraryCategoryService;
import com.spring.springboot.libraryCategory.pojo.LibraryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - - - - - - - - - -
 * library category 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/library")
public class LibraryCategoryController
{
    @Autowired
    LibraryCategoryService libraryCategoryService;

    /**
     * @author SRIC
     *
     * 获取 library category 列表
     */
    @GetMapping("/getLibraryCategoryList")
    public List<LibraryCategory> getLibraryCategoryList()
    {
        return libraryCategoryService.getLibraryCategoryList();
    }
}
