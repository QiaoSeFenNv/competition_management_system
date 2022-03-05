package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollegeInfoService{


    int deleteByPrimaryKey(Integer id);

    int insert(CollegeInfo record);

    int insertSelective(CollegeInfo record);

    CollegeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollegeInfo record);

    int updateByPrimaryKey(CollegeInfo record);

    List<CollegeInfo> findByName(String collegeName);

    List<CollegeInfo> selectByParentId(Long parentId);

    List<CollegeInfo> selectAll();

    List<CollegeInfo> selectDutyId(String userId);

    int deleteByParentId(Long id);

    List<CollegeInfo> selectByAncestors(String s);

    Integer selectByName(String deptId);
}
