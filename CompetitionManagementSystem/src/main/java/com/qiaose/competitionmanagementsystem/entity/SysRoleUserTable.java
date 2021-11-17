package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
    * sys_role_user_table
    */
@Data
public class SysRoleUserTable implements Serializable {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 角色ID
    */
    private String roleId;

    /**
    * 用户ID
    */
    private String userId;

    private static final long serialVersionUID = 1L;
}