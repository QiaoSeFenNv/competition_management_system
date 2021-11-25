package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.ClassTable;

public interface ClassTableMapper {
    int deleteByPrimaryKey(Integer classId);

    int insert(ClassTable record);

    int insertSelective(ClassTable record);

    ClassTable selectByPrimaryKey(Integer classId);

    int updateByPrimaryKeySelective(ClassTable record);

    int updateByPrimaryKey(ClassTable record);
}