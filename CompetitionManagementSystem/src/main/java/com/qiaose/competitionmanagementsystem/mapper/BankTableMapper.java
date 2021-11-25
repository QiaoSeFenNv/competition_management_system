package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.BankTable;

public interface BankTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BankTable record);

    int insertSelective(BankTable record);

    BankTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankTable record);

    int updateByPrimaryKey(BankTable record);
}