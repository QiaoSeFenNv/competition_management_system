package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CounselingInfo;

import java.util.List;

public interface CounselingInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CounselingInfo record);

    int insertSelective(CounselingInfo record);

    CounselingInfo selectByPrimaryKey(Integer id);

    List<CounselingInfo> selectAll();

    int updateByPrimaryKeySelective(CounselingInfo record);

    int updateByPrimaryKey(CounselingInfo record);
}