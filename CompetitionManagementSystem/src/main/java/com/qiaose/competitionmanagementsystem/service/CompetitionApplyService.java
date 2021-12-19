package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApply;

import java.util.List;

public interface CompetitionApplyService{


    int deleteByPrimaryKey(Long id);

    int insert(CompetitionApply record);

    int insertSelective(CompetitionApply record);

    CompetitionApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionApply record);

    int updateByPrimaryKey(CompetitionApply record);

    CompetitionApply selectByUserIdAndInfoId(Long infoId, String userId);

    List<CompetitionApply> selectByUserId(String userId);
}
