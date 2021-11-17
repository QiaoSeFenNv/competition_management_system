package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.SysRoleBackendApiTable;

public interface SysRoleBackendApiTableService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleBackendApiTable record);

    int insertSelective(SysRoleBackendApiTable record);

    SysRoleBackendApiTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleBackendApiTable record);

    int updateByPrimaryKey(SysRoleBackendApiTable record);

    SysRoleBackendApiTable selectByRoleId(String roleId);
}
