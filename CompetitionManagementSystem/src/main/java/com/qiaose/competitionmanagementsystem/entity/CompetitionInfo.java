package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_info
    */
@Data
public class CompetitionInfo implements Serializable {
    /**
    * 竞赛id
    */
    private Long id;

    /**
    * 竞赛名称+届
    */
    private String name;

    /**
     * 竞赛名称id
     */
    private Integer organizerId;

    /**
    * 竞赛报名开始时间
    */
    private Date signupStart;

    /**
    * 竞赛报名截止时间
    */
    private Date signupEnd;

    /**
    * 竞赛地点
    */
    private String location;

    /**
    * 竞赛主办方
    */
    private String sponsor;

    /**
    * 竞赛承办方
    */
    private String undertaker;

    /**
    * 竞赛状态：1:未开始 2:报名中 3:进行中 4：已结束
    */
    private Integer state;

    /**
    * 描述
    */
    private String description;

    /**
    * 竞赛信息创建时间
    */
    private Date createTime;

    /**
    * 比赛级别：A1 A2 B1 B2
    */
    private String level;

    /**
    * 比赛赛道：？？？？
    */
    private Byte track;

    /**
    * 队伍人数
    */
    private String teamNumber;

    /**
    * 是否个人比赛： 0不是，1是
    */
    private Byte isPersonalCompetition;

    /**
    * 竞赛信息更新时间
    */
    @JsonIgnore
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}