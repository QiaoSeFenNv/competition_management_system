package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;

public interface CollegeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollegeInfo record);

    int insertSelective(CollegeInfo record);

    CollegeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollegeInfo record);

    int updateByPrimaryKey(CollegeInfo record);
}