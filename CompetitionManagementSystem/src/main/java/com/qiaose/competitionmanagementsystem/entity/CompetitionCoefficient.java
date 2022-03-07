package com.qiaose.competitionmanagementsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@TableName("competition_coefficient")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_coefficient对象", description = "competition_coefficient")
public class CompetitionCoefficient implements Serializable {
    private static final long serialVersionUID = 717705791840560059L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")

    private Integer id;
    /**
     * approval表中对应的id
     */
    @ApiModelProperty(value = "approval表中对应的id")

    private Long approvalId;
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
     * 第一系数
     */
    @ApiModelProperty(value = "第一系数")

    private Double firstCoefficient;
    /**
     * 第二系数
     */
    @ApiModelProperty(value = "第二系数")

    private Double secordCoefficient;
    /**
     * 第三系数
     */
    @ApiModelProperty(value = "第三系数")

    private Double threeCoefficient;

}
