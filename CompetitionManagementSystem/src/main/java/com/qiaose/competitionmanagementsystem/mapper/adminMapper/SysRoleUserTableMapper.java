package com.qiaose.competitionmanagementsystem.mapper.adminMapper;

import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUserTable record);

    int insertSelective(SysRoleUserTable record);

    SysRoleUserTable selectByPrimaryKey(Integer id);

    List<SysRoleUserTable> selectByUserId(@Param("userId") String userId);

    int deleteByUserId(@Param("id") String id);

    int updateByPrimaryKeySelective(SysRoleUserTable record);

    int updateByPrimaryKey(SysRoleUserTable record);


}