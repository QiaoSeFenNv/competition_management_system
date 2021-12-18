package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
public interface CompetitionBonusService{


    int deleteByPrimaryKey(Long id);

    int insert(CompetitionBonus record);

    int insertSelective(CompetitionBonus record);

    CompetitionBonus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionBonus record);

    int updateByPrimaryKey(CompetitionBonus record);

}
