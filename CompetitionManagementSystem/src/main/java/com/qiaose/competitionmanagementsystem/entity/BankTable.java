package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
    * bank_table
    */
@Data
public class BankTable implements Serializable {
    /**
    * 银行id
    */
    @JsonIgnore
    private Integer id;

    /**
    * 所属银行
    */
    private String belongsBank;

    /**
    * 银行账号
    */
    private Integer bankId;

    /**
    * 开户行
    */
    private String openBank;

    /**
    * 信息创建时间
    */
    @JsonIgnore
    private Date createTime;

    /**
    * 信息更新时间
     *
    */
    @JSONField(serialize = false)   //忽略返回给前端的字段
    @JsonIgnore  //忽略实体类中的字段
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}