package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionTodo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionTodoMapper {
    int deleteByPrimaryKey(Long todoId);

    int insert(CompetitionTodo record);

    int insertSelective(CompetitionTodo record);

    CompetitionTodo selectByPrimaryKey(Long todoId);

    int updateByPrimaryKeySelective(CompetitionTodo record);

    int updateByPrimaryKey(CompetitionTodo record);

    List<CompetitionTodo> selectByApplicantId(@Param("accountName") String accountName);

    List<CompetitionTodo> selectByApprovalId(@Param("approvalId") Long approvalId);
}