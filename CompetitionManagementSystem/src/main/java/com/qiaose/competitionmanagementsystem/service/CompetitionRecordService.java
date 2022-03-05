package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;

import java.util.Collection;
import java.util.List;

public interface CompetitionRecordService{


    int deleteByPrimaryKey(Long recordId);

    int insert(CompetitionRecord record);

    int insertSelective(CompetitionRecord record);

    CompetitionRecord selectByPrimaryKey(Long recordId);

    List<CompetitionRecord> selectAll();

    int updateByPrimaryKeySelective(CompetitionRecord record);

    int updateByPrimaryKey(CompetitionRecord record);

    List<CompetitionRecord> selectByUserId(String userId);
}
