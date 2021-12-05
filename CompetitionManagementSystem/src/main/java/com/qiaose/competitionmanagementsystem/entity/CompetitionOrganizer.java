package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_level
    */
@Data
public class CompetitionOrganizer implements Serializable {
    /**
    * id
    */
    @TableId(type = IdType.AUTO)
    private Integer organizeId;

    /**
    * 比赛级别：A1 A2 B1 B2
    */
    private String organizeLevel;

    /**
    * 主办单位
    */
    private String organizeOrganizer;

    /**
    * 赛事名称
    */
    private String organizeName;

    /**
    * 备注
    */
    private String organizeNote;

    /**
    * 赛事级别创建时间
    */
    @JsonIgnore
    private Date createTime;

    /**
    * 赛事级别信息更新时间
    */
    @JsonIgnore
    @JSONField(serialize = false)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}