package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.SysRoleBackendApiTable;

public interface SysRoleBackendApiTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleBackendApiTable record);

    int insertSelective(SysRoleBackendApiTable record);

    SysRoleBackendApiTable selectByPrimaryKey(Integer id);

    SysRoleBackendApiTable selectByRoleId(String roleId);

    int updateByPrimaryKeySelective(SysRoleBackendApiTable record);

    int updateByPrimaryKey(SysRoleBackendApiTable record);


}