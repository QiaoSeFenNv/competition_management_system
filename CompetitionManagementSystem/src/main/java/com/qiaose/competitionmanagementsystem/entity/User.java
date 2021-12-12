package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * user
    */
@Data
public class User implements Serializable {
    /**
    * id
    */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
    * 用户名 S为学生id T为教师id
    */
    private String userId;

    /**
    * 头像url
    */
    private String userAvatarurl;

    /**
    * 密码
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private String userPassword;

    /**
    * 积分
    */
    private Integer userIntegral;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private Date updatedTime;

    @JsonIgnore
    @TableField(exist = false)
    private String Username;

    @JsonIgnore
    @TableField(exist = false)
    private String userStatus;


    private static final long serialVersionUID = 1L;
}