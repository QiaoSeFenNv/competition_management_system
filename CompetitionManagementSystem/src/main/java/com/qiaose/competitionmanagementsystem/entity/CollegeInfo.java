package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * college_info
    */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeInfo implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 名称
    */
    private String collegeName;

    /**
    * 父id
    */
    private Long parentId;

    /**
    * 祖先
    */
    private String ancestors;

    @TableField(exist = false)
    @JsonIgnore
    private List<CollegeInfo> children;


    private static final long serialVersionUID = 1L;
}