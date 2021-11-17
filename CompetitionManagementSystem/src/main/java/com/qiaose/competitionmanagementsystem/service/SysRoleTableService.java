package com.qiaose.competitionmanagementsystem.service;



import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;

import java.util.List;

public interface SysRoleTableService{


    int deleteByPrimaryKey(String roleId);

    int insert(SysRoleTable record);

    int insertSelective(SysRoleTable record);

    List<SysRoleTable> selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(SysRoleTable record);

    int updateByPrimaryKey(SysRoleTable record);

}
