package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionProgram;

import java.util.List;

public interface CompetitionProgramService{


    int deleteByPrimaryKey(Long id);

    int insert(CompetitionProgram record);

    int insertSelective(CompetitionProgram record);

    CompetitionProgram selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionProgram record);

    int updateByPrimaryKey(CompetitionProgram record);

    List<CompetitionProgram> selectByApproval(Long approvalId);
}
