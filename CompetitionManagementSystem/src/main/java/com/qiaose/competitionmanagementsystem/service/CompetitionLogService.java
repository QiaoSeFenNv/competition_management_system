package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionLog;
public interface CompetitionLogService{


    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionLog record);

    int insertSelective(CompetitionLog record);

    CompetitionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionLog record);

    int updateByPrimaryKey(CompetitionLog record);

}
