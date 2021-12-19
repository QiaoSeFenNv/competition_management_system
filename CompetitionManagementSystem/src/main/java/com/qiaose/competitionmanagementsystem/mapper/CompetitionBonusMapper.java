package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
import org.apache.ibatis.annotations.Param;

public interface CompetitionBonusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompetitionBonus record);

    int insertSelective(CompetitionBonus record);

    CompetitionBonus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionBonus record);

    int updateByPrimaryKey(CompetitionBonus record);

    CompetitionBonus selectByUserIdAndInfoId(@Param("infoId") Long infoId,@Param("userId") String userId);
}