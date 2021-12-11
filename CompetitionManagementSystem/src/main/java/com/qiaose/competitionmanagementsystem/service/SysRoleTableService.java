package com.qiaose.competitionmanagementsystem.service;



import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.dto.SysRoleDto;

import java.util.List;

public interface SysRoleTableService{


    int deleteByPrimaryKey(String roleId);

    int insert(SysRoleTable record);

    int insertSelective(SysRoleTable record);

    List<SysRoleTable> selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(SysRoleTable record);

    int updateByPrimaryKey(SysRoleTable record);

    List<SysRoleTable> selectAll();

    SysRoleTable R_PoToDto(SysRoleDto sysRoleDto);

    SysRoleTable selectByName(String role);

    List<SysRoleTable> selectFindName(String roleName);
}
