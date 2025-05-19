package com.spring.springboot.tools;

import java.io.File;

/**
 * 工具类
 * 对文件夹的相关操作工具
 */

public class FolderEdit
{
    /**
     * 检查指定路径的文件夹是否存在，不存在时自动创建。
     */
    public static boolean checkAndCreateFolder(String folderPath)
    {
        File folder = new File(folderPath);
        if (!folder.exists())
        {
            boolean created = folder.mkdirs();
            if (created)
            {
                System.out.println("文件夹已创建: " + folder.getAbsolutePath());
            }
            else
            {
                System.err.println("无法创建文件夹，请检查路径权限: " + folder.getAbsolutePath());
            }
            return created;
        }
        else
        {
            System.out.println("文件夹已存在: " + folder.getAbsolutePath());
            return true;
        }
    }
}
