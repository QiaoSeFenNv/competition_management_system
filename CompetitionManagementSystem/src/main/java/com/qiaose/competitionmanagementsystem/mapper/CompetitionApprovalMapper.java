package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;

public interface CompetitionApprovalMapper {
    int deleteByPrimaryKey(Long approvalId);

    int insert(CompetitionApproval record);

    int insertSelective(CompetitionApproval record);

    CompetitionApproval selectByPrimaryKey(Long approvalId);

    int updateByPrimaryKeySelective(CompetitionApproval record);

    int updateByPrimaryKey(CompetitionApproval record);
}