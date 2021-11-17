package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
    * sys_role_table
    */
@Data

public class SysRoleTable implements Serializable {
    /**
    * 角色号
    */
    private String roleId;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 描述
    */
    private String description;

    private static final long serialVersionUID = 1L;
}