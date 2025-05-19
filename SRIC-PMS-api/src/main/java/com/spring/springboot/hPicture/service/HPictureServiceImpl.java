package com.spring.springboot.hPicture.service;

import com.spring.springboot.hPicture.mapper.HPictureMapper;
import com.spring.springboot.hPicture.pojo.HPicture;
import com.spring.springboot.hPicture.service.HPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * - - - - - - - - - -
 * picture 服务类
 * - - - - - - - - - -
 */

@Service
public class HPictureServiceImpl implements HPictureService
{
    @Value("${hFilesPath}") //获取application.properties中的全局变量
    private String hFilesPath;

    @Autowired
    HPictureMapper hPictureMapper;

    /**
     * @author SRIC
     *
     * 获取 picture 列表并且进行随机顺序打乱
     */
    @Override
    public List<HPicture> getHPictureRandomList()
    {
        List<HPicture> pictureList =  hPictureMapper.getHPictureList();
        Collections.shuffle(pictureList);
        return pictureList;
    }

    /**
     * @author SRIC
     *
     * 获取 picture 列表数量
     */
    @Override
    public int getHPictureCount()
    {
        return hPictureMapper.getHPictureCount();
    }

    /**
     * @author SRIC
     *
     * 上传 picture 文件
     */
    @Override
    public int uploadPicture(MultipartFile file)
    {
        //检查文件是否为空
        if(file.isEmpty())
        {
            return 400; //若文件为空返回 400
        }

        String originalFilename = file.getOriginalFilename(); //获取文件名称

        //获取文件后缀名称 (不带点)
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains("."))
        {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }

        String pictureFileName = String.valueOf(hPictureMapper.getHPictureCount() + 1) + "." + fileExtension; //配置储存时需使用的文件名称

        HPicture hPicture = new HPicture(); //创建对象 用于数据保存
        hPicture.setFilename(fileExtension);

        //创建文件绝对路径对象
        File saveFile = new File(hFilesPath + "/hPicture/" + pictureFileName);

        //保存文件
        try
        {
            file.transferTo(saveFile);
            System.out.println("保存文件：" + pictureFileName + " 成功");
            hPictureMapper.addPictureMessage(hPicture); //若文件保存成功 则向数据库插入资源信息
            System.out.println("所保存的图片编号：" + hPicture.getId());
            return 200;
        }
        catch (IOException e)
        {
            System.out.println("保存文件：" + pictureFileName + " 失败");
            return 400;
        }
    }
}
