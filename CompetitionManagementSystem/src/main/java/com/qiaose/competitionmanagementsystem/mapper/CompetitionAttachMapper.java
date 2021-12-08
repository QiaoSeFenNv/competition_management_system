package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CompetitionAttach;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompetitionAttachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionAttach record);

    int insertSelective(CompetitionAttach record);

    List<CompetitionAttach> selectByPrimaryKey(Integer id);

    List<CompetitionAttach> selectByPrimaryUserid(Integer id);

    int updateByPrimaryKeySelective(CompetitionAttach record);

    int updateByPrimaryKey(CompetitionAttach record);


}