package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;

public interface SysRoleFrontendMenuTableService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleFrontendMenuTable record);

    int insertSelective(SysRoleFrontendMenuTable record);

    SysRoleFrontendMenuTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleFrontendMenuTable record);

    int updateByPrimaryKey(SysRoleFrontendMenuTable record);

    SysRoleFrontendMenuTable selectByRoleId(String roleId);
}
