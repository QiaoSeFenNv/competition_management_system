package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * competition_process
    */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionProcess implements Serializable {
    /**
    * 流程id
    */
    @TableId(type = IdType.AUTO)
    private Long processId;

    /**
    * 最等级id
    */
    private String topId;

    /**
    * 审核人id
    */
    private String approverId;

    /**
    * 审核人名称
    */
    private String approverName;

    /**
    * 下一个流程id 为空则无
    */
    private Long nextId;

    private static final long serialVersionUID = 1L;
}