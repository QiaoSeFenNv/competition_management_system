package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CollegeClassTable;

import java.util.List;

public interface CollegeClassTableService{


    int deleteByPrimaryKey(Long id);

    int insert(CollegeClassTable record);

    int insertSelective(CollegeClassTable record);

    CollegeClassTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CollegeClassTable record);

    int updateByPrimaryKey(CollegeClassTable record);

    List<CollegeClassTable> getAllClass();
}
