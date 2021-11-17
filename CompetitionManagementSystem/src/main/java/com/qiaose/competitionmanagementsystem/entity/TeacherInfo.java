package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * teacher_info
    */
@Data
public class TeacherInfo implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 教师工号，作为登录账号
    */
    private String teaId;

    /**
    * 教师姓名
    */
    private String name;

    /**
    * 性别
    */
    private String sex;

    /**
    * 职位（讲师，副教授，教授）
    */
    private String position;

    /**
    * 教师信息创建时间
    */
    private Date createTime;

    /**
    * 教师信息更新时间
    */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}