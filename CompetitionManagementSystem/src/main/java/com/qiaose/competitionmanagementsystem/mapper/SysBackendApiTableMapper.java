package com.qiaose.competitionmanagementsystem.mapper;



import com.qiaose.competitionmanagementsystem.entity.SysBackendApiTable;

import java.util.List;

public interface SysBackendApiTableMapper {
    int deleteByPrimaryKey(String backendApiId);

    int insert(SysBackendApiTable record);

    int insertSelective(SysBackendApiTable record);

    SysBackendApiTable selectByPrimaryKey(String backendApiId);

    int updateByPrimaryKeySelective(SysBackendApiTable record);

    int updateByPrimaryKey(SysBackendApiTable record);
}