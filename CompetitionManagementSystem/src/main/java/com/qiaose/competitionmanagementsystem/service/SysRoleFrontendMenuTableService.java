package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;

import java.util.List;

public interface SysRoleFrontendMenuTableService{


    int deleteByPrimaryKey(Long id);

    int insert(SysRoleFrontendMenuTable record);

    int insertSelective(SysRoleFrontendMenuTable record);

    SysRoleFrontendMenuTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleFrontendMenuTable record);

    int updateByPrimaryKey(SysRoleFrontendMenuTable record);

    List<SysRoleFrontendMenuTable> selectByRoleId(String roleId);



    int insertRoleMenu(String roleId, Long menu);

    int deleteByRoleId(String roleId);

    int deleteByAuthorityId(Long id);
}
