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
import org.codehaus.jackson.annotate.JsonIgnore;

@Data
@TableName("competition_course_rep_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_course_rep_record对象", description = "competition_course_rep_record")
public class CompetitionCourseRepRecord implements Serializable {
    private static final long serialVersionUID = 636706628216252814L;
    /**
     * 主键 表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键 表ID")

    private Integer id;
    /**
     * 创建用户id
     */
    @JsonIgnore
    @ApiModelProperty(value = "创建用户id")

    private String userId;
    /**
     * 状态 已开具证明 1/未开具证明 0
     */
    @ApiModelProperty(value = "状态 已开具证明 1/未开具证明 0")

    private Integer status;
    /**
     * 类型 替换学分课程 / 奖励学分课程
     */
    @ApiModelProperty(value = "类型 替换学分课程 / 奖励学分课程")

    private String type;
    /**
     * 课程类型
     */
    @ApiModelProperty(value = "课程类型")

    private String courseType;
    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")

    private String courseName;
    /**
     * 课程学分
     */
    @ApiModelProperty(value = "课程学分")

    private Integer courseCredit;
    /**
     * 使用学分
     */
    @ApiModelProperty(value = "使用学分")

    private Integer creditUsed;
    /**
     * 正考成绩
     */
    @ApiModelProperty(value = "正考成绩")

    private Double courseFormalScore;
    /**
     * 补考成绩
     */
    @ApiModelProperty(value = "补考成绩")

    private Double courseMakeupScore;
    /**
     * 最终分数
     */
    @ApiModelProperty(value = "最终分数")

    private Double courseTargetScore;

}
