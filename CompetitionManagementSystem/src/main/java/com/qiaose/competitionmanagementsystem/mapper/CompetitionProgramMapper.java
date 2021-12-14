package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionProgram;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionProgramMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompetitionProgram record);

    int insertSelective(CompetitionProgram record);

    CompetitionProgram selectByPrimaryKey(Long id);

    CompetitionProgram selectByUserId(@Param("userId") String applicantId);

    int updateByPrimaryKeySelective(CompetitionProgram record);

    int updateByPrimaryKey(CompetitionProgram record);

    List<CompetitionProgram> selectByApproval(Long approvalId);

    CompetitionProgram selectUserIdAndApproval(@Param("userId") String userId,
                                               @Param("approvalId") Long approvalId);
}