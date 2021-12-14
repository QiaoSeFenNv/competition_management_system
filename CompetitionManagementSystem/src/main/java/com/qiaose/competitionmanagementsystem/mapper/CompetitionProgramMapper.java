package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionProgram;

import java.util.List;

public interface CompetitionProgramMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompetitionProgram record);

    int insertSelective(CompetitionProgram record);

    CompetitionProgram selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionProgram record);

    int updateByPrimaryKey(CompetitionProgram record);

    List<CompetitionProgram> selectByApproval(Long approvalId);
}