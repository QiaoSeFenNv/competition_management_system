package com.qiaose.competitionmanagementsystem.mapper.adminMapper;

import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleBackendApiTable;

import java.util.List;

public interface SysRoleBackendApiTableMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByBackApiId(String backendApiId);

    int insert(SysRoleBackendApiTable record);

    int insertSelective(SysRoleBackendApiTable record);

    SysRoleBackendApiTable selectByPrimaryKey(Integer id);

    List<SysRoleBackendApiTable> selectByRoleId(String roleId);



    int updateByPrimaryKeySelective(SysRoleBackendApiTable record);

    int updateByPrimaryKey(SysRoleBackendApiTable record);


}