package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_bonus
    */
@Data
public class CompetitionBonus implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 银行账号
    */
    private Integer cardId;

    /**
    * 开户行
    */
    private String openBank;

    /**
    * 描述
    */
    private String description;

    /**
    * 应发
    */
    private Double shouldSend;

    /**
    * 实发
    */
    @JsonIgnore
    private Double realSend;

    /**
    * 信息创建时间
    */
    private Date createTime;

    /**
    * 信息更新时间
    */
    @JsonIgnore
    private Date updateTime;



    private static final long serialVersionUID = 1L;
}