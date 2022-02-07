package com.qiaose.competitionmanagementsystem.entity.admin;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SysFrontendButtonTable implements Serializable {
    /**
    * 权限id
    */

    private Long id;

    /**
    * 归属菜单id
    */
    @NotNull(message = "parentId 不能为空")
    private Long parentId;

    /**
    * 名称
    */
    @NotEmpty(message = "name 不能为空")
    private String name;

    private static final long serialVersionUID = 1L;
}