package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * competition_record
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String recordWinningStudent;

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
    * 获奖等级
    */
    private Long recordRewardId;

    /**
    * 赛事级别
    */
    private Integer recordLevelId;

    /**
    * 赛事名称
    */
    private Integer recordCompetitionId;

    private static final long serialVersionUID = 1L;
}