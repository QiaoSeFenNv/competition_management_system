package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;
import lombok.Data;

/**
 * sys_frontend_menu_table
 */
@Data
public class SysFrontendMenuTable implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String label;

    /**
     * 描述
     */
    private String describe;

    /**
     * 通用菜单
     * True表示无需分配所有人就可以访问的
     */
    private Boolean isGeneral;

    /**
     * 路径
     */
    private String path;

    /**
     * 重定向
     */
    private String redirect;

    /**
     * 组件
     */
    private String component;

    /**
     * 状态
     */
    private Boolean state;

    /**
     * 排序
     */
    private Integer sortValue;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 分组
     */
    private String group;

    /**
     * 父级菜单ID
     */
    private Long parentId;

    /**
     * 内置
     */
    private Boolean readonly;

    /**
     * 创建人id
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updateTime;


    private List<SysFrontendMenuTable> childrenList;

    private static final long serialVersionUID = 1L;
}