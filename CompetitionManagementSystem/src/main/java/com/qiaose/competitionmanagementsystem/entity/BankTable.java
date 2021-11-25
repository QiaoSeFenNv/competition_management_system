package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
    * bank_table
    */
@Data
public class BankTable implements Serializable {
    /**
    * 银行id
    */
    @TableId(type= IdType.AUTO)
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
    @JSONField(serialize = false)
    private Date createTime;

    /**
    * 信息更新时间
    */
    @JSONField(serialize = false)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}