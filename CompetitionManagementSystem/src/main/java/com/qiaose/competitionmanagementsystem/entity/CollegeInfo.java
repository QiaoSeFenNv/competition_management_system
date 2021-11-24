package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
    * college_info
    */
@Data
public class CollegeInfo implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 二级学院名称
    */
    private String collegeName;

    private static final long serialVersionUID = 1L;
}