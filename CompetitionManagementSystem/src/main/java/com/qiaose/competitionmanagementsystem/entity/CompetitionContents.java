package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class CompetitionContents implements Serializable {
    private Integer id;

    /**
    * 标题
    */
    private String title;

    /**
    * 辅标题
    */
    private String slug;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;

    /**
    * 内容文字
    */
    private String content;

    private Integer authorId;

    /**
    * 状态
    */
    private String status;

    private String tags;

    private String categories;

    private Integer hits;

    private static final long serialVersionUID = 1L;
}