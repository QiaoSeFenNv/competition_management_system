package com.qiaose.competitionmanagementsystem.mapper.adminMapper;

import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendMenuTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFrontendMenuTableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysFrontendMenuTable record);

    int insertSelective(SysFrontendMenuTable record);

    SysFrontendMenuTable selectByPrimaryKey(Long id);

    SysFrontendMenuTable selectByPrimaryKeyTwo(Long id);

    List<SysFrontendMenuTable> selectByParentId(Long id);

    List<SysFrontendMenuTable> selectByAll();

    List<SysFrontendMenuTable> findMenu(@Param("name") String name,@Param("state") Integer state);

    int updateByPrimaryKeySelective(SysFrontendMenuTable record);

    int updateByPrimaryKey(SysFrontendMenuTable record);


}