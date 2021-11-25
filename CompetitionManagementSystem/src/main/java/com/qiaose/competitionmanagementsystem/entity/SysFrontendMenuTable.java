package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * sys_frontend_menu_table
 */
@Data
@JsonIgnoreProperties({"id"})
public class SysFrontendMenuTable implements Serializable {
    /**
     * ID
     */
    @JSONField(serialize=false)
    private Long id;

    /**
     * 名称
     */
    @JSONField(name = "name")
    private String label;

    /**
     * 描述
     */
    @JSONField(serialize=false)
    private String describe;

    /**
     * 通用菜单
     * True表示无需分配所有人就可以访问的
     */
    @JSONField(serialize=false)
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
//    @JSONField(serialize=false)
    private Boolean state;

    /**
     * 排序
     */
//    @JSONField(serialize=false)
    private Integer sortValue;

    /**
     * 菜单图标
     */
//    @JSONField(serialize=false)
    private String icon;

    /**
     * 分组
     */
    @JSONField(serialize=false)
    private String group;

    /**
     * 父级菜单ID
     */
    @JSONField(serialize=false)
    private Long parentId;

    /**
     * 内置
     */
//    @JSONField(serialize=false)
    private Boolean readonly;

    /**
     * 创建人id
     */
//    @JSONField(serialize=false)
    private Long createdBy;

    /**
     * 创建时间
     */
    @JSONField(serialize=false)
    private Date createTime;

    /**
     * 更新人id
     */
    @JSONField(serialize=false)
    private Long updatedBy;

    /**
     * 更新时间
     */
    @JSONField(serialize=false)
    private Date updateTime;


    @Data
    @NoArgsConstructor
    public static class Meta{
        private String icon;
        private String title;
    }

    private Meta meta;

    private List<SysFrontendMenuTable> children;
//    private List<SysFrontendDto> children;



    private static final long serialVersionUID = 1L;
}