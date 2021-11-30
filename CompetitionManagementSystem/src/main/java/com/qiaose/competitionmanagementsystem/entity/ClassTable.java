package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

/**
    * class_table
    */
@Data
public class ClassTable implements Serializable {
    /**
    * 班级id
    */
    @JsonIgnore
    private Integer classId;

    /**
    * 年纪
    */

    private String grade;

    /**
    * 学生所在班级
    */
    private String className;

    /**
    * 学生所属专业
    */
    private String major;

    /**
    * 信息创建时间
    */
    @JsonIgnore
    private Date createTime;

    /**
    * 信息更新时间
    */
    @JSONField(serialize = false)
    @JsonIgnore
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}