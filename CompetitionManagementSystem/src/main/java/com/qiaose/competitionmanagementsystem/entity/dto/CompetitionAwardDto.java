package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.CompetitionAward;
import com.qiaose.competitionmanagementsystem.entity.CompetitionAwardName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName: CompetitionAwardDto
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/2/21
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CompetitionAwardDto {
    List<CompetitionAward> competitionAwardList;
    List<CompetitionAwardName> competitionAwardNameList;
}
