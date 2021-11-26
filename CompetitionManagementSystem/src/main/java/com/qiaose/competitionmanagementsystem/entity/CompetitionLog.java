package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
    * competition_log
    */
@Data
@Builder
public class CompetitionLog implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 执行操作
    */
    private String action;

    /**
    * 执行内容
    */
    private String data;

    /**
    * ip地址
    */
    private String ip;

    /**
    * 创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}