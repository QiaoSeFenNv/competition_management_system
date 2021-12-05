package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionTodo;

import java.util.List;

public interface CompetitionTodoService{


    int deleteByPrimaryKey(Long todoId);

    int insert(CompetitionTodo record);

    int insertSelective(CompetitionTodo record);

    CompetitionTodo selectByPrimaryKey(Long todoId);

    int updateByPrimaryKeySelective(CompetitionTodo record);

    int updateByPrimaryKey(CompetitionTodo record);

    List<CompetitionTodo> selectByApplicantId(String accountName);

    List<CompetitionTodo> selectByApprovalId(Long approvalId);
}
