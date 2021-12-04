package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionReward;

import java.util.List;

public interface CompetitionRewardService{


    int deleteByPrimaryKey(Long rewardId);

    int insert(CompetitionReward record);

    int insertSelective(CompetitionReward record);

    CompetitionReward selectByPrimaryKey(Long rewardId);

    int updateByPrimaryKeySelective(CompetitionReward record);

    int updateByPrimaryKey(CompetitionReward record);

    List<CompetitionReward> selectAll();
}
