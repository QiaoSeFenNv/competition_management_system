package com.qiaose.competitionmanagementsystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
    * user
    */
@Data
public class User implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 用户名
    */
    private String accountName;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updatedTime;

    /**
    * openid
    */
    private String openId;

    /**
    * 验证时间
    */
    private Date verificationTime;

    /**
    * 电话
    */
    private String telephone;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 头像url
    */
    private String avatarurl;

    /**
    * 密码
    */
    private String password;

    /**
    * 学号
    */

    private String stuId;

    /**
    * 工号
    */
    private String teaId;

    /**
    * 角色号
    */
    private String roleId;

    /**
    * 管理号
    */
    private String manageId;

    /**
    * 积分
    */
    private Integer integral;

    private static final long serialVersionUID = 1L;
}