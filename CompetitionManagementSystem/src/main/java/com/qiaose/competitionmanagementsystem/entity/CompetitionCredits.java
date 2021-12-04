package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_credits
    */
@Data
public class CompetitionCredits implements Serializable {
    /**
    * credit_id
    */
    @TableId(type = IdType.AUTO)
    private Integer creditId;

    /**
    * 比赛级别：A1 A2 B1 B2
    */
    private String creditLevel;

    /**
    * 分值
    */
    private Integer creditCredits;

    /**
    * 等奖
    */
    private Long rewardId;

    /**
    * 奖励级别创建时间
    */
    @JsonIgnore
    private Date createTime;

    /**
    * 奖励级别信息更新时间
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private Date updateTime;

    //前端不应该输入这条数据，而是作为显示作用，可以拿到。到时候上传还是放在id中
    @JsonIgnore
    @TableField(exist = false)
    private String reward_name;


    private static final long serialVersionUID = 1L;
}