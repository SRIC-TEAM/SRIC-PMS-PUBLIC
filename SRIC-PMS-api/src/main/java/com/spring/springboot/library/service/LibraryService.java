package com.spring.springboot.library.service;

import com.spring.springboot.library.pojo.*;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

public interface LibraryService
{
    List<Library> getLibraryList(LibraryListRequestPojo libraryRequest);
    int getLibraryCount(LibraryListRequestPojo libraryRequest);
    LibraryDataResponsePojo getLibraryById(int id);
    boolean editLibraryData(LibraryEditRequestPojo libraryEditRequest);
    AddLibraryResponsePojo addLibrary(Library library);
    int editLibraryMessage(Library library);
}
