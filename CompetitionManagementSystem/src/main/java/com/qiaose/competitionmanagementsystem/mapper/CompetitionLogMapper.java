package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionLog;

public interface CompetitionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionLog record);

    int insertSelective(CompetitionLog record);

    CompetitionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionLog record);

    int updateByPrimaryKey(CompetitionLog record);
}