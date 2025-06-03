package com.spring.springboot.resourcesCategory.service;

import com.spring.springboot.resourcesCategory.mapper.ResourcesCategoryMapper;
import com.spring.springboot.resourcesCategory.pojo.ResourcesCategory;
import com.spring.springboot.resourcesCategory.pojo.ResourcesCategoryManageData;
import com.spring.springboot.resourcesCategory.service.ResourcesCategoryService;
import com.spring.springboot.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * - - - - - - - - - -
 * resources category 服务类
 * - - - - - - - - - -
 */

@Service
public class ResourcesCategoryServiceImpl implements ResourcesCategoryService
{
    @Autowired
    ResourcesCategoryMapper resourcesCategoryMapper;

    /**
     * @author SRIC
     *
     * 获取 resources category 列表
     */
    @Override
    public List<ResourcesCategory> getResourcesCategoryList()
    {
        return resourcesCategoryMapper.getResourcesCategoryList();
    }

    /**
     * @author SRIC
     *
     * 获取 resources category 列表
     * 并且获取 category 下 resources 的条数
     */
    @Override
    public List<ResourcesCategoryManageData> getResourcesCategoryManageDataList()
    {
        return resourcesCategoryMapper.getResourcesCategoryManageDataList();
    }

    /**
     * @author SRIC
     *
     * 添加或者修改 resources category
     */
    @Override
    public int addOrUpdateResourcesCategory(ResourcesCategory resourcesCategory)
    {
        if(resourcesCategoryMapper.getResourcesCategoryCountByName(resourcesCategory) > 0) //若已有重复的名称
        {
            return 400;
        }
        else
        {
            if(resourcesCategory.getId() == 0)
            {
                resourcesCategoryMapper.addResourcesCategory(resourcesCategory);
            }
            else
            {
                resourcesCategoryMapper.updateResourcesCategory(resourcesCategory);
            }
            return 200;
        }
    }
}
