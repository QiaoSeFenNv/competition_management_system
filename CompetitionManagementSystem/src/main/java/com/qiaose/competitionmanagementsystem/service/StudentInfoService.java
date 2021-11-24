package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.StudentInfo;

import java.util.List;

public interface StudentInfoService{


    int deleteByPrimaryKey(Integer id);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);

    List<StudentInfo> selectByDeptId(Integer deptId);
}
