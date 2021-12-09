package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.ClassTable;

public interface ClassTableMapper {
    int deleteByPrimaryKey(Long classId);

    int insert(ClassTable record);

    int insertSelective(ClassTable record);

    ClassTable selectByPrimaryKey(Long classId);

    int updateByPrimaryKeySelective(ClassTable record);

    int updateByPrimaryKey(ClassTable record);
}