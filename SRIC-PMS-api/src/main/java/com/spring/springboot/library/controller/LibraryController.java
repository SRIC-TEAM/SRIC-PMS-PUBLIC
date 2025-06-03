package com.spring.springboot.library.controller;

import com.spring.springboot.library.pojo.LibraryDataResponsePojo;
import com.spring.springboot.library.pojo.LibraryListRequestPojo;
import com.spring.springboot.library.pojo.LibraryListResponsePojo;
import com.spring.springboot.library.service.LibraryService;
import com.spring.springboot.library.pojo.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * - - - - - - - - - -
 * library 控制类
 * - - - - - - - - - -
 */

@CrossOrigin
@RestController
@RequestMapping("/api/library")
public class LibraryController
{
    @Autowired
    LibraryService libraryService;

    /**
     * @author SRIC
     *
     * 获取 library 分页列表
     */
    @GetMapping("/getLibraryList")
    public LibraryListResponsePojo getLibraryList(LibraryListRequestPojo libraryRequest)
    {
        LibraryListResponsePojo libraryResponse = new LibraryListResponsePojo();
        libraryResponse.setLibraryList(libraryService.getLibraryList(libraryRequest));
        libraryResponse.setTotal(libraryService.getLibraryCount(libraryRequest));
        return libraryResponse;
    }

    /**
     * @author SRIC
     *
     * 根据 id 获取 library 数据
     */
    @GetMapping("/getLibraryById")
    public LibraryDataResponsePojo getLibraryById(Library library)
    {
        return libraryService.getLibraryById(library.getId());
    }
}
