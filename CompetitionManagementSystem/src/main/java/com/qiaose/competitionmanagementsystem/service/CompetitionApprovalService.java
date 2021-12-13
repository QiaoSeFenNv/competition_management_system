package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;

import java.util.List;

public interface CompetitionApprovalService{


    int deleteByPrimaryKey(Long approvalId);

    int insert(CompetitionApproval record);

    int insertSelective(CompetitionApproval record);

    CompetitionApproval selectByPrimaryKey(Long approvalId);

    int updateByPrimaryKeySelective(CompetitionApproval record);

    int updateByPrimaryKey(CompetitionApproval record);

    CompetitionApproval SendApproval(Long approvalId, Long contentId, UserInfo userInfo, Long processId) ;

    List<CompetitionApproval> selectByApplicantId(String userId);
}
