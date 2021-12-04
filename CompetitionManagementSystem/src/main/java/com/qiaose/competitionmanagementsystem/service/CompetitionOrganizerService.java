package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionOrganizer;

import java.util.List;

public interface CompetitionOrganizerService{


    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionOrganizer record);

    int insertSelective(CompetitionOrganizer record);

    CompetitionOrganizer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionOrganizer record);

    int updateByPrimaryKey(CompetitionOrganizer record);

    List<CompetitionOrganizer> selectAll();

    List<CompetitionOrganizer> findOrganize(String organize);
}
