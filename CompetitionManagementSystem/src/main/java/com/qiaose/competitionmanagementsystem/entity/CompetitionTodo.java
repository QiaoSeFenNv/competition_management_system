package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * competition_todo
    */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompetitionTodo implements Serializable {
    /**
    * id
    */
    @TableId(type = IdType.AUTO)
    private Long todoId;

    /**
    * 申请表id
    */
    private Long approvalId;

    /**
    * 申请id 学号
    */
    private String applicantId;

    /**
    * 是否处理状态
    */
    private Byte todoStatus;

    /**
    * 事务类型
    */
    private String todoType;

    /**
     * 申请人名称
     */
    private String applicantName;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 完成时间
    */
    private Date completeTime;

    private String todoAdvice;


    private String applicationId;

    private String applicationName;

    private static final long serialVersionUID = 1L;


}