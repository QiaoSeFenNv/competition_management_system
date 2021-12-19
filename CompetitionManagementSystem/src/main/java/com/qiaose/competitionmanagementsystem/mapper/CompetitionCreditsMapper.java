package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionCredits;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionCreditsMapper {
    int deleteByPrimaryKey(Integer creditId);

    int insert(CompetitionCredits record);

    int insertSelective(CompetitionCredits record);

    CompetitionCredits selectByPrimaryKey(Integer creditId);

    int updateByPrimaryKeySelective(CompetitionCredits record);

    int updateByPrimaryKey(CompetitionCredits record);

    List<CompetitionCredits> getAllCredit();

    CompetitionCredits selectByNameAndId(@Param("recordRewardId") Long recordRewardId,@Param("recordLevelName") String recordLevelName);


}