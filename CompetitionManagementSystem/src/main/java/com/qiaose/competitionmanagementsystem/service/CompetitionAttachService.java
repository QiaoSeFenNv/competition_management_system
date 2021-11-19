package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionAttach;

import java.util.List;

public interface CompetitionAttachService{


    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionAttach record);

    int insertSelective(CompetitionAttach record);

    List<CompetitionAttach> selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionAttach record);

    int updateByPrimaryKey(CompetitionAttach record);

    int deleteByIdTime(Integer id, Integer created,String ftype);

    List<CompetitionAttach> selectByPrimaryUserid(Integer id);
}
