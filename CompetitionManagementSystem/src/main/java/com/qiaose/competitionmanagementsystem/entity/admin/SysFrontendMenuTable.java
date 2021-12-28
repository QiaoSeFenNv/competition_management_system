package com.qiaose.competitionmanagementsystem.entity.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @JSONField(serialize=false)
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
    @JSONField(serialize=false)
    private Long createdBy;

    /**
     * 创建时间
     */
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
        private JSONObject routeMeta;
    }

    private Meta meta;

    /**
     * 元信息
     */
    private String routeMeta;


    private List<SysFrontendMenuTable> children;



    private static final long serialVersionUID = 1L;
}