package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApply;
public interface CompetitionApplyService{


    int deleteByPrimaryKey(Long id);

    int insert(CompetitionApply record);

    int insertSelective(CompetitionApply record);

    CompetitionApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionApply record);

    int updateByPrimaryKey(CompetitionApply record);

    CompetitionApply selectByUserIdAndInfoId(Integer infoId, String userId);
}
