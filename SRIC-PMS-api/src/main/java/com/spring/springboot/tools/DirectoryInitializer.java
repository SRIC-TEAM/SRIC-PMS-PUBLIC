package com.spring.springboot.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 工具类
 * 初始化项目所需的文件夹
 */

@Component
public class DirectoryInitializer implements ApplicationRunner {

    //获取application.properties中的全局变量
    @Value("${filesPath}")
    private String filesPath;
    @Value("${hFilesPath}")
    private String hFilesPath;

    @Override
    public void run(ApplicationArguments args)
    {
        System.out.println("----- 初始化文件夹 -----");

        // 检查和创建根目录下的 hComic 文件夹
        String hComicPath = hFilesPath + File.separator + "hComic";
        FolderEdit.checkAndCreateFolder(hComicPath);

        // 检查和创建根目录下的 hPicture 文件夹
        String hPicturePath = hFilesPath + File.separator + "hPicture";
        FolderEdit.checkAndCreateFolder(hPicturePath);

        // 检查和创建根目录下的 hVideo 文件夹
        String hVideoPath = hFilesPath + File.separator + "hVideo";
        FolderEdit.checkAndCreateFolder(hVideoPath);

        // 检查和创建根目录下的 hVideoCover 文件夹
        String hVideoCoverPath = hFilesPath + File.separator + "hVideoCover";
        FolderEdit.checkAndCreateFolder(hVideoCoverPath);

        // 检查和创建根目录下的 library 文件夹
        String libraryPath = filesPath + File.separator + "library";
        FolderEdit.checkAndCreateFolder(libraryPath);

        // 检查和创建根目录下的 resources 文件夹
        String resourcesPath = filesPath + File.separator + "resources";
        FolderEdit.checkAndCreateFolder(resourcesPath);

        // 检查和创建根目录下的 userAvatar 文件夹
        String userAvatarPath = filesPath + File.separator + "userAvatar";
        FolderEdit.checkAndCreateFolder(userAvatarPath);
    }
}
