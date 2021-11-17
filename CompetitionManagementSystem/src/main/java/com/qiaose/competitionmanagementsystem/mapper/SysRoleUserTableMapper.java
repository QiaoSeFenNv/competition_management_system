package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.SysRoleUserTable;

public interface SysRoleUserTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUserTable record);

    int insertSelective(SysRoleUserTable record);

    SysRoleUserTable selectByPrimaryKey(Integer id);

    SysRoleUserTable selectByRoleId(String userId);

    int updateByPrimaryKeySelective(SysRoleUserTable record);

    int updateByPrimaryKey(SysRoleUserTable record);
}