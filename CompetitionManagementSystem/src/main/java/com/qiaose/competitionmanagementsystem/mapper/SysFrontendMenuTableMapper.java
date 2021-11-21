package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;

import java.util.List;

public interface SysFrontendMenuTableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysFrontendMenuTable record);

    int insertSelective(SysFrontendMenuTable record);

    SysFrontendMenuTable selectByPrimaryKey(Long id);

    SysFrontendMenuTable selectByPrimaryKeyTwo(Long id);

    List<SysFrontendMenuTable> selectByParentId(Long id);

    List<SysFrontendMenuTable> selectByAll();

    int updateByPrimaryKeySelective(SysFrontendMenuTable record);

    int updateByPrimaryKey(SysFrontendMenuTable record);


}