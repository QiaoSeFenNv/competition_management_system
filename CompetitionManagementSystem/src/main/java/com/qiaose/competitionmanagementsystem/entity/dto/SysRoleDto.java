package com.qiaose.competitionmanagementsystem.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysRoleDto {

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


    private Integer[] menu;
}
