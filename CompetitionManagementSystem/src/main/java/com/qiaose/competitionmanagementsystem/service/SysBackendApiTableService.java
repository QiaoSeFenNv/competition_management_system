package com.qiaose.competitionmanagementsystem.service;



import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;

import java.util.List;

public interface SysBackendApiTableService{


    int deleteByPrimaryKey(String backendApiId);

    int insert(SysBackendApiTable record);

    int insertSelective(SysBackendApiTable record);

    SysBackendApiTable selectByPrimaryKey(String backendApiId);

    int updateByPrimaryKeySelective(SysBackendApiTable record);

    int updateByPrimaryKey(SysBackendApiTable record);

}
