package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionContents;
public interface CompetitionContentsService{


    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionContents record);

    int insertSelective(CompetitionContents record);

    CompetitionContents selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionContents record);

    int updateByPrimaryKey(CompetitionContents record);

}
