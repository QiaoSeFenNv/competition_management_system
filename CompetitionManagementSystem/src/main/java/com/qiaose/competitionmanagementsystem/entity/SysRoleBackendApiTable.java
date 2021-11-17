package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
    * sys_role_backend_api_table
    */
@Data
public class SysRoleBackendApiTable implements Serializable {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 角色ID
    */
    private String roleId;

    /**
    * API管理表ID
    */
    private String backendApiId;

    private static final long serialVersionUID = 1L;
}