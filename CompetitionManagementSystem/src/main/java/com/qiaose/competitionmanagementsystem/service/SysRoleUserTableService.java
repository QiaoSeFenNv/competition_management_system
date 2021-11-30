package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.SysRoleUserTable;

import java.util.List;

public interface SysRoleUserTableService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUserTable record);

    int insertSelective(SysRoleUserTable record);

    SysRoleUserTable selectByPrimaryKey(Integer id);

    List<SysRoleUserTable> selectByRoleId(String userId);

    int updateByPrimaryKeySelective(SysRoleUserTable record);

    int updateByPrimaryKey(SysRoleUserTable record);


}
