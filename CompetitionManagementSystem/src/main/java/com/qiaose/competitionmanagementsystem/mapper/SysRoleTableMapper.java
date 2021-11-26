package com.qiaose.competitionmanagementsystem.mapper;



import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;

import java.util.List;

public interface SysRoleTableMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(SysRoleTable record);

    int insertSelective(SysRoleTable record);

    List<SysRoleTable> selectByPrimaryKey(String roleId);

    List<SysRoleTable> selectAll();

    List<SysRoleTable> selectFindName(String roleName);

    SysRoleTable selectByName(String role);

    int updateByPrimaryKeySelective(SysRoleTable record);

    int updateByPrimaryKey(SysRoleTable record);


}