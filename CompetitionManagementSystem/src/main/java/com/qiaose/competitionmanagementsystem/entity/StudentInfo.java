package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
    * student_info
    */
@Data
public class StudentInfo implements Serializable {
    /**
    * id
    */
    @TableId(type= IdType.AUTO)
    private Integer id;

    /**
    * 学生的学号，作为账号登录
    */
    @NotNull(message = "stuId 不能为空")
    private String stuId;

    /**
    * 学生姓名
    */
    @Size(max = 20)
    @NotNull(message = "name 不能为空")
    private String name;

    /**
    * 证件号
    */
    private String idNumber;

    /**
    * 证件类型
    */
    private String idType;

    /**
    * 学籍状态
    */
    private String studentStatus;

    /**
    * 辅导员
    */
    private String counselor;

    /**
    * 国籍
    */
    private String nationality;

    /**
    * 籍贯
    */
    private String nativePlace;

    /**
    * 民族
    */
    private String national;

    /**
    * 二级学院id
    */
    @JsonIgnore
    private Integer deptId;

    /**
    * 电话
    */
    private String telephone;

    /**
    * 邮箱
    */
    private String email;

    /**
    * qq
    */
    private String qq;

    /**
    * 微信
    */
    private String wechat;

    /**
    * 银行id
    */
    @JsonIgnore
    private Integer bankId;

    /**
    * 班级id
    */
    @JsonIgnore
    private Integer classId;

    /**
    * 学生信息创建时间
    */
    @JsonIgnore
    private Date createTime;

    /**
    * 学生信息更新时间
    */
    @JSONField(serialize = false)
    @JsonIgnore
    private Date updateTime;

    @TableField(exist = false)
    private BankTable bankTable;

    @TableField(exist = false)
    private ClassTable classTable;

    @TableField(exist = false)
    private CollegeInfo collegeInfo;


    private static final long serialVersionUID = 1L;
}