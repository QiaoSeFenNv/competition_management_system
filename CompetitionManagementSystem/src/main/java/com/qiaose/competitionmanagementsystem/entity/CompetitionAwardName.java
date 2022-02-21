package com.qiaose.competitionmanagementsystem.entity;


import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("competition_award_name")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_award_name对象", description = "competition_award_name")
public class CompetitionAwardName implements Serializable {
    private static final long serialVersionUID = -92066000225715231L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;
    /**
     * 竞赛名称
     */
    @ApiModelProperty(value = "竞赛名称")
    private String competitionInfoName;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /**
     * 获奖时间
     */
    @ApiModelProperty(value = "获奖时间")
    private Date awardTime;
    /**
     * 获奖名称
     */
    @ApiModelProperty(value = "获奖名称")
    private String rewardName;

    @ApiModelProperty(value = "记录类型")
    //不给输入的机会
    @JsonIgnore
    private Byte recordType;

}
