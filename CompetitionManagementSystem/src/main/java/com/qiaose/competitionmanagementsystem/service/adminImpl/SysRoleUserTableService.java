package com.qiaose.competitionmanagementsystem.service.adminImpl;

import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;

import java.util.List;

public interface SysRoleUserTableService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUserTable record);

    int insertSelective(SysRoleUserTable record);

    SysRoleUserTable selectByPrimaryKey(Integer id);

    List<SysRoleUserTable> selectByUserId(String userId);

    int updateByPrimaryKeySelective(SysRoleUserTable record);

    int updateByPrimaryKey(SysRoleUserTable record);

    int deleteByUserId(String id);
}
