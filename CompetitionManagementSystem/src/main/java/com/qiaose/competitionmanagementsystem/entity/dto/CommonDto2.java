package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: CommonDto2
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/3/19
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CommonDto2 {
    private Integer sumCredit;
    private Integer sumReward;
    private List<User> stuRanking;
    private List<AwardCompetitionDto> compReward;
    private List<CreditDistributionDto> creditDistribution;
}
