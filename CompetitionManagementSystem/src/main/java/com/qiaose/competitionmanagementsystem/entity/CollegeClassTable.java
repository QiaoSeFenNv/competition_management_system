package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class CollegeClassTable implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 二级学院id
    */
    private Integer collegeId;

    /**
    * classid
    */
    private Long classId;

    private static final long serialVersionUID = 1L;
}