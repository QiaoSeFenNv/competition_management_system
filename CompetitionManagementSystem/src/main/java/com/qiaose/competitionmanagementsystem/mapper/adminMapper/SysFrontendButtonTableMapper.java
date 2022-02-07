package com.qiaose.competitionmanagementsystem.mapper.adminMapper;

import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendButtonTable;

import java.util.List;

public interface SysFrontendButtonTableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysFrontendButtonTable record);

    int insertSelective(SysFrontendButtonTable record);

    SysFrontendButtonTable selectByPrimaryKey(Long id);

    List<SysFrontendButtonTable> selectAll();

    int updateByPrimaryKeySelective(SysFrontendButtonTable record);

    int updateByPrimaryKey(SysFrontendButtonTable record);

    List<SysFrontendButtonTable> selectByName(String name);
}