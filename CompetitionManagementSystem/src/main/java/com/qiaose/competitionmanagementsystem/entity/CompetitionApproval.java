package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_approval
    */
@Data
public class CompetitionApproval implements Serializable {
    /**
    * 主键
    */
    private Long approvalId;

    /**
    * 申请id 学号
    */
    private String applicantId;

    /**
    * 申请名称 学生名称
    */
    private String applicantName;

    /**
    * 申请内容id
    */
    private Long applicantContentid;

    /**
    * 审批状态
    */
    private Byte approvalStatus;

    /**
    * 流程编号
    */
    private Long processId;

    /**
    * 审判驳回理由
    */
    @JsonIgnore
    private String rejectReson;

    /**
    * 完成时间
    */
    private Date completeTime;

    /**
    * 创建时间
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private Date createTime;

    /**
    * 更新时间
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}