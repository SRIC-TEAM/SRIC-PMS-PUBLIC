package com.spring.springboot.libraryCategory.service;

import com.spring.springboot.libraryCategory.pojo.LibraryCategory;
import com.spring.springboot.libraryCategory.pojo.LibraryCategoryManageData;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

public interface LibraryCategoryService
{
    List<LibraryCategory> getLibraryCategoryList();
    List<LibraryCategoryManageData> getLibraryCategoryManageDataList();
    int addOrUpdateLibraryCategory(LibraryCategory libraryCategory);
}
