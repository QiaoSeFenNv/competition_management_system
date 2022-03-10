package com.qiaose.competitionmanagementsystem.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: PriceDto
 * @Description: 奖金接口返回类型
 * @Author qiaosefennv
 * @Date 2022/3/3
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {
    private static final long serialVersionUID = 524476956356081888L;

    private Integer id;

    private String collegeInfo;

    private String collegeInfoName;

    List<StudentDto> studentDtoList = new ArrayList<>();


    private String competitionInfo;

    private String competitionInfoName;

    private String oganizer;
    /**
     * 赛事类别
     */

    private String competitionType;
    /**
     * 赛事级别
     */

    private String competitionLevel;
    /**
     * 项目名称
     */

    private String titleName;
    /**
     * 获奖等级
     */

    private String competitionAward;
    /**
     * 获奖时间
     */

    private String competitionAwardName;


    private Long awardTime;
    /**
     * 模式
     */

    private String model;
    /**
     * 指导教师
     */

    private String teacher;
    /**
     * 积分
     */

    private Integer integral;
    /**
     * 奖金
     */

    private Double money;

    private Byte status;

    private CompetitionBonus competitionBonus;

}
