package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionSlideshow;

import java.util.List;

public interface CompetitionSlideshowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionSlideshow record);

    int insertSelective(CompetitionSlideshow record);

    CompetitionSlideshow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionSlideshow record);

    int updateByPrimaryKey(CompetitionSlideshow record);

    List<CompetitionSlideshow> selectAll();
}