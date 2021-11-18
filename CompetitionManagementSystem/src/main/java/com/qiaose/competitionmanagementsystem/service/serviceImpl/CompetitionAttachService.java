package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.qiaose.competitionmanagementsystem.entity.CompetitionAttach;
public interface CompetitionAttachService{


    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionAttach record);

    int insertSelective(CompetitionAttach record);

    CompetitionAttach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionAttach record);

    int updateByPrimaryKey(CompetitionAttach record);

}
