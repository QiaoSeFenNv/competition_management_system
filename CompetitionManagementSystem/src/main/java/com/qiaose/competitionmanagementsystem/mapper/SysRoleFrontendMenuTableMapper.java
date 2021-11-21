package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;

import java.util.List;

public interface SysRoleFrontendMenuTableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleFrontendMenuTable record);

    int insertSelective(SysRoleFrontendMenuTable record);

    SysRoleFrontendMenuTable selectByPrimaryKey(Long id);

    List<SysRoleFrontendMenuTable> selectByRoleId(Long roleId);

    int updateByPrimaryKeySelective(SysRoleFrontendMenuTable record);

    int updateByPrimaryKey(SysRoleFrontendMenuTable record);


}