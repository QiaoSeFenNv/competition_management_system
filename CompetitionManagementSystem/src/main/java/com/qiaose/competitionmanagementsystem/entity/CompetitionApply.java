package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * competition_apply
    */
@Data
public class CompetitionApply implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 竞赛id
    */
    private Integer infoId;

    /**
    * 参赛学生
    */
    private String applystudent;

    /**
    * 信息创建时间
    */
    private Date createTime;

    /**
    * 信息更新时间
    */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}