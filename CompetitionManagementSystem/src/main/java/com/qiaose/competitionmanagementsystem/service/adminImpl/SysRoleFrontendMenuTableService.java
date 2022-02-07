package com.qiaose.competitionmanagementsystem.service.adminImpl;

import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleFrontendMenuTable;

import java.util.List;

public interface SysRoleFrontendMenuTableService{


    int deleteByPrimaryKey(Long id);

    int insert(SysRoleFrontendMenuTable record);

    int insertSelective(SysRoleFrontendMenuTable record);

    SysRoleFrontendMenuTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleFrontendMenuTable record);

    int updateByPrimaryKey(SysRoleFrontendMenuTable record);

    List<SysRoleFrontendMenuTable> selectByRoleId(String roleId);



    int insertRoleMenu(String roleId, Long menu,String type);

    int deleteByRoleId(String roleId);

    int deleteByAuthorityId(Long id);

    List<Long> selectByRoleAndType(String roleId, String perm);
}
