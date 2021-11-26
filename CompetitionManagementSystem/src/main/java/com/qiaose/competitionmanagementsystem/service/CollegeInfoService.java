package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;

import java.util.List;

public interface CollegeInfoService{


    int deleteByPrimaryKey(Integer id);

    int insert(CollegeInfo record);

    int insertSelective(CollegeInfo record);

    CollegeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollegeInfo record);

    int updateByPrimaryKey(CollegeInfo record);

    List<CollegeInfo> findByName(String collegeName);


    CollegeInfo selectByName(String collegeName);

    List<CollegeInfo> selectAll();
}
