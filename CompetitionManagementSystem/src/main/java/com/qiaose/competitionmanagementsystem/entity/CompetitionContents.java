package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetitionContents implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 辅标题
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String slug;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 内容文字
     */
    private String content;

    @JsonIgnore
    @JSONField(serialize = false)
    private Integer authorId;

    /**
     * 状态
     */
    private String status;

    @JsonIgnore
    @JSONField(serialize = false)
    private String tags;
    @JsonIgnore
    @JSONField(serialize = false)
    private String categories;

    private Integer hits;

    private static final long serialVersionUID = 1L;
}