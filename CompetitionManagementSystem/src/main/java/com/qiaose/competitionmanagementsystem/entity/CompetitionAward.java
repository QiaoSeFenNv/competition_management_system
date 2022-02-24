package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@TableName("competition_award")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_award对象", description = "competition_award")
public class CompetitionAward implements Serializable {
    private static final long serialVersionUID = 265625993426437790L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;
    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    private String userId;
    /**
     * 竞赛id
     */
    @ApiModelProperty(value = "竞赛id")
    private Long compId;
    /**
     * 获奖时间
     */
    @ApiModelProperty(value = "获奖时间")
    private Date awardTime;
    /**
     * 获奖级别
     */
    @ApiModelProperty(value = "获奖级别")
    private Long rewardLevel;
    /**
     * 申请id
     */
    @ApiModelProperty(value = "申请id")
    @JsonIgnore
    private Long relateApproval;
    /**
     * 记录类型
     */
    @ApiModelProperty(value = "记录类型")
    //不给输入的机会
    @JsonIgnore
    private Byte recordType;


    @TableField(exist = false)
    @ApiModelProperty(value = "获奖级别名称")
    private String rewardLevelName;

    @TableField(exist = false)
    @ApiModelProperty(value = "竞赛名称")
    private String compName;

    @TableField(exist = false)
    @ApiModelProperty(value = "比赛名称")
    private String organizeName;



}
