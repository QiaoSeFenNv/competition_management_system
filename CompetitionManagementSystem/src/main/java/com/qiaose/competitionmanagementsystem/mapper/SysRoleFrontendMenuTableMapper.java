package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;

public interface SysRoleFrontendMenuTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleFrontendMenuTable record);

    int insertSelective(SysRoleFrontendMenuTable record);

    SysRoleFrontendMenuTable selectByPrimaryKey(Integer id);

    SysRoleFrontendMenuTable selectByRoleId(String roleId);

    int updateByPrimaryKeySelective(SysRoleFrontendMenuTable record);

    int updateByPrimaryKey(SysRoleFrontendMenuTable record);


}