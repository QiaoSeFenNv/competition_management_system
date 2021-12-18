package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompetitionInfo record);

    int insertSelective(CompetitionInfo record);

    CompetitionInfo selectByPrimaryKey(Long id);

    List<CompetitionInfo> selectByAll();

    List<CompetitionInfo> selectByState(@Param("state") Integer state);

    int updateByPrimaryKeySelective(CompetitionInfo record);

    int updateByPrimaryKey(CompetitionInfo record);


}