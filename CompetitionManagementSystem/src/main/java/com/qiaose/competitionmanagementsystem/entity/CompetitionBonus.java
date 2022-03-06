package com.qiaose.competitionmanagementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@TableName("competition_bonus")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "competition_bonus对象", description = "competition_bonus")
public class CompetitionBonus implements Serializable {

    private static final long serialVersionUID = -45761659756128161L;

    public interface SecurityData {
    }
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;
    /**
     * 获奖表id
     */
    @ApiModelProperty(value = "获奖表id")
    private Integer priceId;
    /**
     * 学号
     */

    @ApiModelProperty(value = "学号")
    private String userId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String userName;
    /**
     * 证照类型
     */
    @ApiModelProperty(value = "证照类型")
    private String cardType;
    /**
     * 证照号码
     */
    @ApiModelProperty(value = "证照号码")
    private String idCard;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;
    /**
     * 应发金额（元）
     */
    @ApiModelProperty(value = "应发金额（元）")
    private Double bonus;
    /**
     * 收税
     */
    @ApiModelProperty(value = "收税")
    private Double tax;
    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    private String openBlank;
    /**
     * 银行卡账号
     */
    @ApiModelProperty(value = "银行卡账号")
    private String bankAccount;

    /**
     * 奖金状态
     */
    @ApiModelProperty(value = "奖金状态")
    private Byte status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;



    /**
     * 赛事名称
     */
    @TableField(exist = false)
    private String competitionInfo;
    /**
     * 组织者
     */
    @TableField(exist = false)
    private String oganizer;
    /**
     * 赛事类别
     */
    @TableField(exist = false)
    private String competitionType;
    /**
     * 赛事级别
     */
    @TableField(exist = false)
    private String competitionLevel;
    /**
     * 项目名称
     */
    @TableField(exist = false)
    private String titleName;
    @TableField(exist = false)
    private String competitionAward;

}
