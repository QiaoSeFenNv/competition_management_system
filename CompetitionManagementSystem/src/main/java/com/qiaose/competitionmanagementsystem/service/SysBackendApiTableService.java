package com.qiaose.competitionmanagementsystem.service;



import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleBackendApiTable;

import java.util.List;

public interface SysBackendApiTableService{


    int deleteByPrimaryKey(String backendApiId);

    int insert(SysBackendApiTable record);

    int insertSelective(SysBackendApiTable record);

    SysBackendApiTable selectByPrimaryKey(String backendApiId);

    List<SysBackendApiTable> selectByAll();

    int updateByPrimaryKeySelective(SysBackendApiTable record);

    int updateByPrimaryKey(SysBackendApiTable record);

}
