package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionContents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionContentsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionContents record);

    int insertSelective(CompetitionContents record);

    CompetitionContents selectByPrimaryKey(Integer id);

    List<CompetitionContents> findContent(@Param("title") String title);

    int updateByPrimaryKeySelective(CompetitionContents record);

    int updateByPrimaryKey(CompetitionContents record);

    List<CompetitionContents> selectALl();


}