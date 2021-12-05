package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionOrganizer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionOrganizerMapper {
    int deleteByPrimaryKey(Integer organizeId);

    int insert(CompetitionOrganizer record);

    int insertSelective(CompetitionOrganizer record);

    CompetitionOrganizer selectByPrimaryKey(Integer organizeId);

    int updateByPrimaryKeySelective(CompetitionOrganizer record);

    int updateByPrimaryKey(CompetitionOrganizer record);

    List<CompetitionOrganizer> selectAll();

    List<CompetitionOrganizer> findOrganize(@Param("organize") String organize);
}