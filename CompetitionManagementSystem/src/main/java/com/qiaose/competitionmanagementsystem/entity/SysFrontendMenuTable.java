package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
    * sys_frontend_menu_table
    */
@Data
public class SysFrontendMenuTable implements Serializable {
    /**
    * 主键
    */
    private String frontendMenuId;

    /**
    * 前端菜单名称
    */
    private String frontendMenuName;

    /**
    * 前端菜单访问HTML地址
    */
    private String frontendMenuUrl;

    /**
    * 父ID
    */
    private String pid;

    /**
    * 排序
    */
    private Integer frontendMenuSort;

    /**
    * 描述
    */
    private String description;

    private static final long serialVersionUID = 1L;
}