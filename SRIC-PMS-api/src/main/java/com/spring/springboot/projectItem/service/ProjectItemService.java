package com.spring.springboot.projectItem.service;

import com.spring.springboot.projectItem.pojo.ProjectItem;
import com.spring.springboot.projectItem.pojo.ProjectItemListRequestPojo;

import java.util.List;

public interface ProjectItemService
{
    List<ProjectItem> getProjectItemList(ProjectItemListRequestPojo projectItemListRequest);
    int getProjectItemCount(ProjectItemListRequestPojo projectItemListRequest);
    int endProjectItem(ProjectItem projectItem);
    int pickProjectItemUser(ProjectItem projectItem);
    int addProjectItem(ProjectItem projectItem);
    int updateProjectItemEnded(ProjectItem projectItem);
    int updateProjectItemText(ProjectItem projectItem);
}
