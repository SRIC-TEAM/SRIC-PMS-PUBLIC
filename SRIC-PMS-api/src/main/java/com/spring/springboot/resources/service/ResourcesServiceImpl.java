package com.spring.springboot.resources.service;

import com.spring.springboot.resources.mapper.ResourcesMapper;
import com.spring.springboot.resources.pojo.Resources;
import com.spring.springboot.resources.pojo.ResourcesListRequestPojo;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * - - - - - - - - - -
 * notes 服务类
 * - - - - - - - - - -
 */

@Service
public class ResourcesServiceImpl implements ResourcesService
{
    @Value("${filesPath}") //获取application.properties中的全局变量
    private String filesPath;

    @Autowired
    ResourcesMapper resourcesMapper;

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表 分页
     */
    @Override
    public List<Resources> getResourcesList(ResourcesListRequestPojo resourcesListRequest)
    {
        resourcesListRequest.setLimitSize(20); //一页获取20条数据
        resourcesListRequest.setLimitBefore((resourcesListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return resourcesMapper.getResourcesList(resourcesListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表数量
     */
    @Override
    public int getResourcesCount(ResourcesListRequestPojo resourcesListRequest)
    {
        return resourcesMapper.getResourcesCount(resourcesListRequest);
    }

    /**
     * @author SRIC
     *
     * 更新 resources 数据信息
     */
    @Override
    public int editResourcesMessage(Resources resources)
    {
        if(resourcesMapper.getResourcesCountByName(resources) > 0) //已有重复名称
        {
            return 400;
        }
        else
        {
            resourcesMapper.updateResourcesMessage(resources);
            return 200;
        }
    }

    /**
     * @author SRIC
     *
     * 检查 resources 是否已有名称
     */
    @Override
    public int checkResourcesName(String name)
    {
        if(resourcesMapper.checkResourcesName(name) > 0) //匹配的名称大于 0 条
        {
            return 400; //已有名称
        }
        else
        {
            return 200; //未有名称
        }
    }

    /**
     * @author SRIC
     *
     * 上传 resources 数据
     * 储存上传的文件到 /resources
     * 将 resources 信息记录到数据库中
     */
    @Override
    public int uploadResources(MultipartFile file, Resources resources)
    {
        //检查文件是否为空
        if(file.isEmpty())
        {
            return 400;
        }

        String originalFilename = file.getOriginalFilename(); //获取文件名称
        String newFilename = System.currentTimeMillis() + "-" + originalFilename; //使用时间戳 + 上传文件名称作为文件储存名称

        //获取文件后缀名称
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains("."))
        {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        //设置需要储存的文件属性
        resources.setFilename(newFilename);
        resources.setSize(String.format("%.3f", (double) file.getSize() / 1000 / 1000));
        resources.setType(fileExtension);

        //创建文件绝对路径对象
        File saveFile = new File(filesPath + "/resources/" + newFilename);

        //保存文件
        try
        {
            file.transferTo(saveFile);
            System.out.println("保存文件：" + newFilename + " 成功");
            resourcesMapper.addResourcesMessage(resources); //若文件保存成功 则向数据库插入资源信息
            return 200;
        }
        catch (IOException e)
        {
            System.out.println("保存文件：" + newFilename + " 失败");
            return 400;
        }
    }

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表 分页
     * 根据权限等级筛选
     */
    @Override
    public List<Resources> getResourcesListWithPermission(ResourcesListRequestPojo resourcesListRequest)
    {
        resourcesListRequest.setLimitSize(20); //一页获取20条数据
        resourcesListRequest.setLimitBefore((resourcesListRequest.getPage() - 1) * 20); //当前获取数据之前的空缺 （如第一页空缺0条 第二页空缺20条）
        return resourcesMapper.getResourcesListWithPermission(resourcesListRequest);
    }

    /**
     * @author SRIC
     *
     * 获取 resources 数据列表数量
     * 根据权限等级筛选
     */
    @Override
    public int getResourcesCountWithPermission(ResourcesListRequestPojo resourcesListRequest)
    {
        return resourcesMapper.getResourcesCountWithPermission(resourcesListRequest);
    }

    /**
     * @author SRIC
     *
     * 根据 uuid 获取 资源信息
     * resources 作为传递 uuid 的对象
     */
    public Resources getResourcesByUuid(Resources resources)
    {
        return resourcesMapper.getResourcesByUuid(resources);

    }
}
