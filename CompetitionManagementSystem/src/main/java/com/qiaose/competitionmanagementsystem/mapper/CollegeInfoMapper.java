package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollegeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByParentId(@Param("parentId") Long parentId);

    int insert(CollegeInfo record);

    int insertSelective(CollegeInfo record);

    CollegeInfo selectByPrimaryKey(Integer id);

    List<CollegeInfo> selectAll();

    List<CollegeInfo> findByName(@Param("collegeName") String collegeName);

    List<CollegeInfo> selectByParentId(@Param("id") Long id);

    List<CollegeInfo> selectByAncestors(@Param("s")String s);

    int updateByPrimaryKeySelective(CollegeInfo record);

    int updateByPrimaryKey(CollegeInfo record);


}