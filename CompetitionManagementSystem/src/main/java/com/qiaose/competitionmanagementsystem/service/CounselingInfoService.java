package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.CounselingInfo;

import java.util.List;

public interface CounselingInfoService{


    int deleteByPrimaryKey(Integer id);

    int insert(CounselingInfo record);

    int insertSelective(CounselingInfo record);

    CounselingInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CounselingInfo record);

    int updateByPrimaryKey(CounselingInfo record);

    List<CounselingInfo> selectAll();

}
