package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.dto.SysFrontendDto;

import java.util.List;

public interface SysFrontendMenuTableService{


    int deleteByPrimaryKey(Long id);

    int insert(SysFrontendMenuTable record);

    int insertSelective(SysFrontendMenuTable record);

    SysFrontendMenuTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysFrontendMenuTable record);

    int updateByPrimaryKey(SysFrontendMenuTable record);

    List<SysFrontendMenuTable> selectByParentId(Long id);


    SysFrontendMenuTable selectByPrimaryKeyTwo(long id);



    List<SysFrontendMenuTable> listWithTree(Long id);

    SysFrontendMenuTable F_DtoToF_Po(SysFrontendDto sysFrontendDto);

    List<SysFrontendMenuTable> selectByAll();

    List<SysFrontendMenuTable> findMenu(String name, Integer state);
}
