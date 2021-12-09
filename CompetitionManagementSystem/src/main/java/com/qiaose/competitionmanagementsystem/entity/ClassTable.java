package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * class_table
    */
@Data
public class ClassTable implements Serializable {
    /**
    * 该表id
    */
    private Long classId;

    /**
    * 学生所在专业
    */
    private String majorClass;

    /**
    * 班级负责人
    */
    private String classManger;

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