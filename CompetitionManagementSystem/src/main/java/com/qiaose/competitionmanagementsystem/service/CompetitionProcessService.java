package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionProcess;
public interface CompetitionProcessService{


    int deleteByPrimaryKey(Long processId);

    int insert(CompetitionProcess record);

    int insertSelective(CompetitionProcess record);

    CompetitionProcess selectByPrimaryKey(Long processId);

    int updateByPrimaryKeySelective(CompetitionProcess record);

    int updateByPrimaryKey(CompetitionProcess record);

}
