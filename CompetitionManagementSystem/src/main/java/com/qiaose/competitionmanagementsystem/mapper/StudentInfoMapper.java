package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.StudentInfo;

import java.util.List;

public interface StudentInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer id);

    List<StudentInfo> selectByDeptId(Integer deptId);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);


}