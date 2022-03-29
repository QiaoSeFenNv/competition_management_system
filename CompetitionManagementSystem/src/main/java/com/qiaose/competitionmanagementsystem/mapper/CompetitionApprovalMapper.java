package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionApprovalMapper {
    int deleteByPrimaryKey(Long approvalId);

    int insert(CompetitionApproval record);

    int insertSelective(CompetitionApproval record);

    CompetitionApproval selectByPrimaryKey(Long approvalId);

    Long selectByRecordId(Long recordId);

    List<CompetitionApproval> selectByApplicantId(@Param("userId") String userId);

    int updateByPrimaryKeySelective(CompetitionApproval record);

    int updateByPrimaryKey(CompetitionApproval record);

    List<CompetitionApproval> selectAllSuccessApproval();
}