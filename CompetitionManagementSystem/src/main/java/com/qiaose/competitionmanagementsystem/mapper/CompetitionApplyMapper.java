package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompetitionApply record);

    int insertSelective(CompetitionApply record);

    CompetitionApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionApply record);

    int updateByPrimaryKey(CompetitionApply record);

    CompetitionApply selectByUserIdAndInfoId(@Param("infoId") Long infoId,@Param("userId") String userId);

    List<CompetitionApply> selectByUserId(@Param("userId") String userId);
}