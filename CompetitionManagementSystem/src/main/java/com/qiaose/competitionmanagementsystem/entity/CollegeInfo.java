package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
    * college_info
    */
@Data
public class CollegeInfo implements Serializable {
    /**
    * id
    */
    @TableId(type= IdType.AUTO)
    private Integer id;

    /**
    * 二级学院名称
    */
    private String collegeName;

    private static final long serialVersionUID = 1L;
}