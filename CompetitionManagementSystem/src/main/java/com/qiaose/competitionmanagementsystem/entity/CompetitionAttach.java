package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
    * competition_attach
    */
@Data
public class CompetitionAttach implements Serializable {
    /**
    * 文件id
    */
    private Integer id;

    /**
    * 文件名称
    */
    private String fname;

    /**
    * 文件类型
    */
    private String ftype;

    /**
    * 存储路径
    */
    private String fkey;

    /**
    * 所属者
    */
    private Long userId;

    /**
    * 创建时间
    */
    private Integer created;

    private static final long serialVersionUID = 1L;
}