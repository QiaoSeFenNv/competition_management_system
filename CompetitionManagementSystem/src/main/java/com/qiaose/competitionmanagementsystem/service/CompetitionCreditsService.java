package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionCredits;

import java.util.List;

public interface CompetitionCreditsService{


    int deleteByPrimaryKey(Integer creditId);

    int insert(CompetitionCredits record);

    int insertSelective(CompetitionCredits record);

    CompetitionCredits selectByPrimaryKey(Integer creditId);

    int updateByPrimaryKeySelective(CompetitionCredits record);

    int updateByPrimaryKey(CompetitionCredits record);

    List<CompetitionCredits> getAllCredit();

    CompetitionCredits selectByNameAndId(Long recordRewardId, String recordLevelName);

}
