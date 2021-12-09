package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CollegeClassTable;

import java.util.List;

public interface CollegeClassTableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CollegeClassTable record);

    int insertSelective(CollegeClassTable record);

    CollegeClassTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CollegeClassTable record);

    int updateByPrimaryKey(CollegeClassTable record);

    List<CollegeClassTable> getAllClass();
}