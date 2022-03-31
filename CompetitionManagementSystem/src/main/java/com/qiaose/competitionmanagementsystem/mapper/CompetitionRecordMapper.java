package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.entity.dto.AwardCompetitionDto;

import java.util.List;

public interface CompetitionRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(CompetitionRecord record);

    int insertSelective(CompetitionRecord record);

    CompetitionRecord selectByPrimaryKey(Long recordId);

    List<CompetitionRecord> selectAll();

    List<CompetitionRecord> selectByUserId(String userId);

    int updateByPrimaryKeySelective(CompetitionRecord record);

    int updateByPrimaryKey(CompetitionRecord record);

    List<AwardCompetitionDto> getTotalData();

    List<CompetitionRecord> selectByWinningStudent(String userId);
}