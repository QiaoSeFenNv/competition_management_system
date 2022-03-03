package com.qiaose.competitionmanagementsystem.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("competition_price")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_price对象", description = "competition_price")
public class CompetitionPrice implements Serializable {
    private static final long serialVersionUID = 524476956356081888L;
    /**
     * id主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id主键")
    private Integer id;
    /**
     * 二级学院名称
     */
    @ApiModelProperty(value = "二级学院名称")
    private String collegeInfo;
    /**
     * 年级
     */
    @ApiModelProperty(value = "年级")
    private String grade;
    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    private String userId;
    /**
     * 学生数组
     */
    @ApiModelProperty(value = "学生数组")
    private String student;
    /**
     * 赛事名称
     */
    @ApiModelProperty(value = "赛事名称")
    private String competitionInfo;
    /**
     * 组织者
     */
    @ApiModelProperty(value = "组织者")
    private String oganizer;
    /**
     * 赛事类别
     */
    @ApiModelProperty(value = "赛事类别")
    private String competitionType;
    /**
     * 赛事级别
     */
    @ApiModelProperty(value = "赛事级别")
    private String competitionLevel;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String titleName;
    /**
     * 获奖等级
     */
    @ApiModelProperty(value = "获奖等级")
    private String competitionAward;
    /**
     * 获奖时间
     */
    @ApiModelProperty(value = "获奖时间")
    private Date awardTime;
    /**
     * 模式
     */
    @ApiModelProperty(value = "模式")
    private String model;
    /**
     * 指导教师
     */
    @ApiModelProperty(value = "指导教师")
    private String teacher;
    /**
     * 积分
     */
    @ApiModelProperty(value = "积分")
    private Integer integral;
    /**
     * 奖金
     */
    @ApiModelProperty(value = "奖金")
    private Double money;


}
