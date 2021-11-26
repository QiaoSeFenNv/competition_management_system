package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollegeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollegeInfo record);

    int insertSelective(CollegeInfo record);

    CollegeInfo selectByPrimaryKey(Integer id);

    List<CollegeInfo> findByName(@Param(value = "collegeName") String collegeName);

    List<CollegeInfo> selectAll();

    CollegeInfo selectByName(String collegeName);

    int updateByPrimaryKeySelective(CollegeInfo record);

    int updateByPrimaryKey(CollegeInfo record);


}