package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
    * sys_frontend_menu_table
    */
@Data
public class SysRoleFrontendMenuTable implements Serializable {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 角色ID
    */
    private String roleId;

    /**
    * 前端菜单管理ID
    */
    private String frontendMenuId;

    private static final long serialVersionUID = 1L;
}