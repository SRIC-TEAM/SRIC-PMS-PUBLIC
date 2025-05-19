package com.spring.springboot.project.service;

import com.spring.springboot.project.pojo.Project;
import com.spring.springboot.project.pojo.ProjectListRequestPojo;
import com.spring.springboot.project.pojo.ProjectUserRelationPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectService
{
    List<Project> getProjectList(ProjectListRequestPojo projectListRequest);
    int getProjectCount();
    int addOrUpdateProject(Project project);
    List<Project> getProjectListWithUserList(ProjectListRequestPojo projectListRequest);
    int deleteProjectUserRelation(ProjectUserRelationPojo projectUserRelation);
    int insertProjectUserRelation(ProjectUserRelationPojo projectUserRelation);
    List<Project> getProjectListWithUserListByUser(ProjectListRequestPojo projectListRequest);
    Project getProjectByUuidWithUserList(String uuid);
    int checkProjectUser(String userUuid,String projectUuid);
}
