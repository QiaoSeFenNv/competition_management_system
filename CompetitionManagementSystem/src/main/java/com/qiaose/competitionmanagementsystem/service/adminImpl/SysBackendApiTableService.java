package com.qiaose.competitionmanagementsystem.service.adminImpl;



import com.qiaose.competitionmanagementsystem.entity.admin.SysBackendApiTable;

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
