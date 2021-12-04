package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_reward
    */
@Data
public class CompetitionReward implements Serializable {
    /**
    * id
    */
    @TableId(type = IdType.AUTO)
    private Long rewardId;

    /**
    * 奖励级别：x等奖
    */
    private String rewardLevel;

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

    private static final long serialVersionUID = 1L;
}