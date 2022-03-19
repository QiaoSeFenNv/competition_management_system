package com.qiaose.competitionmanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("competition_coefficient")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_coefficient对象", description = "competition_coefficient")
public class CompetitionCoefficient implements Serializable {
    private static final long serialVersionUID = -97444426755700395L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
     * 第一梯度的学生
     */
    @ApiModelProperty(value = "第一梯度的学生")
    private String first;
    /**
     * 第二梯度的学生
     */
    @ApiModelProperty(value = "第二梯度的学生")
    private String secord;
    /**
     * 第三梯度的学生
     */
    @ApiModelProperty(value = "第三梯度的学生")
    private String threed;
    /**
     * 竞赛信息id
     */
    @ApiModelProperty(value = "竞赛信息id")
    private Long competitionInfoId;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    /**
     * 成员人数
     */
    @ApiModelProperty(value = "成员人数")
    private Integer memberCount;
    /**
     * 组别
     */
    @ApiModelProperty(value = "组别")
    private String group;
    /**
     * 名次
     */
    @ApiModelProperty(value = "名次")
    private Long rank;
    /**
     * 系数
     */
    @ApiModelProperty(value = "系数")
    private Double gradient;
    /**
     * 学生
     */
    @ApiModelProperty(value = "学生")
    private String studentList;

}
