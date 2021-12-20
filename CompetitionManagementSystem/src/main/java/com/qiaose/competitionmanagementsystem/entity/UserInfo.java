package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * user
    */
@Data
public class UserInfo implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 用户名 S为学生id T为教师id
    */
    private String userId;

    /**
    * 用户姓名
    */
    private String userName;

    /**
    * 学籍状态
    */
    private String userStatus;

    /**
    * 辅导员
    */
    @JsonIgnore
    private String counselor;

    /**
    * 部门id
    */
    private String deptId;

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
    @JsonIgnore
    private String qq;

    /**
    * 微信
    */
    @JsonIgnore
    private String wechat;

    /**
    * 银行id
    */
    @JsonIgnore
    private Integer bankId;

    /**
    * 创建时间
    */
    @JsonIgnore
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updatedTime;


    private String[] role;

    private static final long serialVersionUID = 1L;
}