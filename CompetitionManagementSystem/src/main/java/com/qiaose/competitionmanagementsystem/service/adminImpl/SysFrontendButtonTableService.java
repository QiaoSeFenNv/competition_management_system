package com.qiaose.competitionmanagementsystem.service.adminImpl;

import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendButtonTable;

import java.util.List;

public interface SysFrontendButtonTableService{


    int deleteByPrimaryKey(Long id);

    int insert(SysFrontendButtonTable record);

    int insertSelective(SysFrontendButtonTable record);

    SysFrontendButtonTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysFrontendButtonTable record);

    int updateByPrimaryKey(SysFrontendButtonTable record);

    List<SysFrontendButtonTable> selectAll();

    List<SysFrontendButtonTable> selectByName(String name);
}
