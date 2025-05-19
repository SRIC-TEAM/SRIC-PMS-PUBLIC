package com.spring.springboot.project.pojo;

import lombok.Data;

/**
 * - - - - - - - - - -
 * project 与 user 的 关系 实体类
 * - - - - - - - - - -
 */

@Data
public class ProjectUserRelationPojo
{
    private String uuid;
    private String userUuid;
    private String projectUuid;
}
