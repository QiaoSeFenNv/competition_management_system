package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_role_frontend_menu_table
 */
@Data
public class SysRoleFrontendMenuTable implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 资源id
     * #c_resource #c_menu
     */
    private Long authorityId;

    /**
     * 权限类型
     * #AuthorizeType{MENU:菜单;RESOURCE:资源;}
     */
    private String authorityType;

    /**
     * 角色id
     * #c_role
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createdBy;

    private static final long serialVersionUID = 1L;
}