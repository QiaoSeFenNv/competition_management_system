package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;

import java.util.List;

public interface CompetitionRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(CompetitionRecord record);

    int insertSelective(CompetitionRecord record);

    CompetitionRecord selectByPrimaryKey(Long recordId);

    List<CompetitionRecord> selectAll();

    int updateByPrimaryKeySelective(CompetitionRecord record);

    int updateByPrimaryKey(CompetitionRecord record);


}