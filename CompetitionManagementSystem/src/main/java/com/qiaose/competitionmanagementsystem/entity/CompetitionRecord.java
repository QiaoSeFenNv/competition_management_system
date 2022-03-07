package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
    * competition_record
    */
@Data
public class CompetitionRecord implements Serializable {
    /**
    * id
    */
    private Long recordId;

    /**
    * 项目名称
    */
    private String recordName;



    /**
    * 个人团体
    */
    private String recordIndivGroups;

    /**
    * 指导老师
    */
    private String recordInstructor;

    /**
    * 获奖时间
    */
    private Date recordWinningTime;

    /**
    * 颁奖单位
    */
    private String recordLocation;

    /**
    * 获奖学生
    */
    @JsonIgnore
    private String recordWinningStudent;



    /**
     * 附件路径
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String recordUpload;

    @TableField(exist = false)
    private String[] recordUploads;


    /**
    * 申请学分
    */
    private String recordApplyCredit;

    /**
    * 年纪专业
    */
    private String recordGrade;

    /**
    * 二级学院
    */
    private Integer recordCollegeId;

    /**
     * 二级学院
     */
    private String recordCollegeName;

    /**
    * 获奖等级
    */
    private Long recordRewardId;

    /**
     * 等级名称
     */
    @TableField(exist = false)
    private String recordRewardName;

    /**
    * 赛事级别
    */
    private String recordLevelName;

    /**
    * 赛事名称
    */
    private Integer recordCompetitionId;

    /**
     * 赛事名称
     */
    @TableField(exist = false)
    private String recordCompetitionName;



    @TableField(exist = false)
    private Long todoId;

    /*
    * 类型
    * */
    private String recordType;
    /*
     * 梯度
     * */
    private String recordGradient;
    /*
     * 系数
     * */
    private String recordCoefficient;

    private List<CompetitionProgram> competitionProgramList;



    private static final long serialVersionUID = 1L;
}