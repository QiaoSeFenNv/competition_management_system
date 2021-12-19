package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApply;
import org.apache.ibatis.annotations.Param;

public interface CompetitionApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CompetitionApply record);

    int insertSelective(CompetitionApply record);

    CompetitionApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CompetitionApply record);

    int updateByPrimaryKey(CompetitionApply record);

    CompetitionApply selectByUserIdAndInfoId(@Param("infoId") Integer infoId,@Param("userId") String userId);
}