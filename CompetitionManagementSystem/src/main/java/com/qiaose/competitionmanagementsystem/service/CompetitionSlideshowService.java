package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CompetitionSlideshow;
public interface CompetitionSlideshowService{


    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionSlideshow record);

    int insertSelective(CompetitionSlideshow record);

    CompetitionSlideshow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionSlideshow record);

    int updateByPrimaryKey(CompetitionSlideshow record);

}
