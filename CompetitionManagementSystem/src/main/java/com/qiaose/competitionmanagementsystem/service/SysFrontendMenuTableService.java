package com.qiaose.competitionmanagementsystem.service;



import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;

import java.util.List;

public interface SysFrontendMenuTableService{


    int deleteByPrimaryKey(String frontendMenuId);

    int insert(SysFrontendMenuTable record);

    int insertSelective(SysFrontendMenuTable record);

    List<SysFrontendMenuTable> selectByPrimaryKey(String frontendMenuId);

    int updateByPrimaryKeySelective(SysFrontendMenuTable record);

    int updateByPrimaryKey(SysFrontendMenuTable record);

}
