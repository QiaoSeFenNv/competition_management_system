package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionInfo;

import java.util.List;

public interface CompetitionInfoService{


    int deleteByPrimaryKey(Long id);

    int insert(CompetitionInfo record);

    int insertSelective(CompetitionInfo record);

    CompetitionInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionInfo record);

    int updateByPrimaryKey(CompetitionInfo record);

    List<CompetitionInfo> selectByAll();

    List<CompetitionInfo> selectByState(Integer state);
}
