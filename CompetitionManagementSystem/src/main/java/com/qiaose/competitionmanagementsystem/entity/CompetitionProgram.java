package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompetitionProgram implements Serializable {
    /**
    * id
    */
    private Long id;

    /**
    * 状态
    */
    private Byte state;

    /**
    * 审核人
    */
    private String auditor;

    /**
    * 当前步骤
    */
    private String stepname;

    /**
    * 申请表id，返回数据时就靠它
    */
    private Long approvalId;


    /**
     * 申请表id，返回数据时就靠它
     */
    private Date complete;

    /**
     * 负责人id
     */
    private String userId;

    /**
     * 备注
     */
    private String advice;

    private static final long serialVersionUID = 1L;
}