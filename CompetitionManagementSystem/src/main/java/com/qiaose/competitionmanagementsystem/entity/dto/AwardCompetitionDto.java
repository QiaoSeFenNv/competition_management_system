package com.qiaose.competitionmanagementsystem.entity.dto;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: AwardCompetitionDto
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/3/19
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class AwardCompetitionDto {


    private String competitionInfo;

    private Integer competitionInfoId;

    private Integer value;
}
