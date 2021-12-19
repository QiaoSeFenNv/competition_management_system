package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionReward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionRewardMapper {
    int deleteByPrimaryKey(Long rewardId);

    int insert(CompetitionReward record);

    int insertSelective(CompetitionReward record);

    CompetitionReward selectByPrimaryKey(Long rewardId);

    int updateByPrimaryKeySelective(CompetitionReward record);

    int updateByPrimaryKey(CompetitionReward record);

    List<CompetitionReward> selectAll();

    CompetitionReward selectByName(@Param("name") String name);
}