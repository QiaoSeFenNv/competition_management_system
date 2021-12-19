package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_apply
    */
@Data
public class CompetitionApply implements Serializable {
    /**
    * id
    */
    @JsonIgnore
    private Long id;

    /**
    * 竞赛id
    */
    private Long infoId;

    /**
    * 参赛学生
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private String applystudent;

    @TableField(exist = false)
    private String[] applystudents;

    /**
    * 信息创建时间
    */
    @JsonIgnore
    @JSONField
    private Date createTime;

    /**
    * 信息更新时间
    */
    private Date updateTime;

    private String reward;

    private static final long serialVersionUID = 1L;
}