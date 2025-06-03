package com.spring.springboot.library.service;

import com.spring.springboot.library.mapper.LibraryMapper;
import com.spring.springboot.library.pojo.*;
import com.spring.springboot.libraryCategory.mapper.LibraryCategoryMapper;
import com.spring.springboot.response.ResponseCode;
import com.spring.springboot.tools.FileEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * library 服务类
 * - - - - - - - - - -
 */

@Service
public class LibraryServiceImpl implements LibraryService
{
    @Value("${filesPath}") //获取application.properties中的全局变量
    private String filesPath;

    @Autowired
    private FileEdit fileEdit;

    @Autowired
    LibraryMapper libraryMapper;

    @Autowired
    LibraryCategoryMapper libraryCategoryMapper;

    /**
     * @author SRIC
     *
     * 获取 library 列表数据
     * 设置空缺是为了方便 sql 语句进行查询
     */
    @Override
    public List<Library> getLibraryList(LibraryListRequestPojo libraryRequest)
    {
        libraryRequest.setLimitSize(20); //一页获取20条数据
        libraryRequest.setLimitBefore((libraryRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return libraryMapper.getLibraryList(libraryRequest);
    }

    /**
     * @author SRIC
     *
     * 获取符合条件的 library 的条数 用于分页显示传入 total
     */
    @Override
    public int getLibraryCount(LibraryListRequestPojo libraryRequest)
    {
        return libraryMapper.getLibraryCount(libraryRequest);
    }

    /**
     * @author SRIC
     *
     * 根据 id 获取 library
     * 通过工具类获取文件内容
     */
    @Override
    public LibraryDataResponsePojo getLibraryById(int id)
    {
        LibraryDataResponsePojo libraryDataResponse = new LibraryDataResponsePojo();
        Library library = libraryMapper.getLibraryById(id);
        libraryDataResponse.setLibrary(library);
        libraryDataResponse.setLibraryDataText(fileEdit.readFileToString(filesPath + "/library/" + library.getFilename()));
        return libraryDataResponse;
    }

    /**
     * @author SRIC
     *
     * 将字符串写入对应 id 的文件
     */
    @Override
    public boolean editLibraryData(LibraryEditRequestPojo libraryEditRequest)
    {
        return fileEdit.writeFile( filesPath + "/library/" + libraryEditRequest.getFilename(),libraryEditRequest.getContent());
    }

    /**
     * @author SRIC
     *
     * 添加 library
     * 先添加一条数据 将 title 与 category 插入
     * 后根据自增的 id 更新 filename
     */
    @Override
    public AddLibraryResponsePojo addLibrary(Library library)
    {
        AddLibraryResponsePojo addLibraryResponse = new AddLibraryResponsePojo();
        if(libraryMapper.getLibraryCountByTitle(library) > 0) //已有重复名称
        {
            addLibraryResponse.setCode(400);
        }
        else
        {
            libraryMapper.addLibrary(library);
            library.setFilename(library.getId() + ".md");
            libraryMapper.updateLibraryFilename(library);
            if(fileEdit.createFile(library.getId() + ".md",filesPath + "/library/"))
            {
                addLibraryResponse.setCode(200);
                addLibraryResponse.setId(library.getId());
            }
            else
            {
                addLibraryResponse.setCode(500);
            }
        }
        return addLibraryResponse;
    }

    /**
     * @author SRIC
     *
     * 修改 library 信息
     */
    @Override
    public int editLibraryMessage(Library library)
    {
        if(libraryMapper.getLibraryCountByTitle(library) > 0) //已有重复名称
        {
            return 400;
        }
        else
        {
            libraryMapper.updateLibraryMessage(library);
            return 200;
        }
    }
}
