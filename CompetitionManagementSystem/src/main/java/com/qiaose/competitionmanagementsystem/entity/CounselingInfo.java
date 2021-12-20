package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * counseling_info
    */
@Data
public class CounselingInfo implements Serializable {
    /**
    * id
    */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
    * 单位名称
    */
    private String orgName;

    /**
    * 辅导内容
    */
    private String counseContent;

    /**
    * 费用
    */
    private BigDecimal money;

    /**
    * 时长
    */
    private Date duration;

    /**
    * 形式
    */
    private String type;

    /**
    * 嘉宾
    */
    private String guest;

    /**
    * 日期
    */
    private Date cDate;

    /**
    * 辅导事项
    */
    private String counseling;

    private static final long serialVersionUID = 1L;
}