package com.qiaose.competitionmanagementsystem.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
    * sys_role_table
    */
@Data

public class SysRoleTable implements Serializable {
    /**
    * 角色号
    */
    @TableId(type= IdType.AUTO)
    private String roleId;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 描述
    */
    private String description;

    @TableField(exist = false)
    private List<Long> menu;

    @TableField(exist = false)
    private List<Long> perm;
    

    private static final long serialVersionUID = 1L;
}