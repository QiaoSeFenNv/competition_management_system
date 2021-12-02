package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
    * competition_slideshow
    */
@Data
public class CompetitionSlideshow implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 图片地址
    */
    private String name;

    /**
    * 文章链接
    */
    private String link;

    /**
    * 存储路径
    */
    private String path;

    /**
    * 状态
    */
    private Byte state;

    /**
    * 学生信息创建时间
    */
    @JsonIgnore
    private Date createTime;

    private static final long serialVersionUID = 1L;
}