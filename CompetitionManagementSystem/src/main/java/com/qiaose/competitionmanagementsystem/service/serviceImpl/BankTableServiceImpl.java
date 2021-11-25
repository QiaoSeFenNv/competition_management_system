package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.BankTableMapper;
import com.qiaose.competitionmanagementsystem.entity.BankTable;
import com.qiaose.competitionmanagementsystem.service.BankTableService;
@Service
public class BankTableServiceImpl implements BankTableService{

    @Resource
    private BankTableMapper bankTableMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return bankTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BankTable record) {
        return bankTableMapper.insert(record);
    }

    @Override
    public int insertSelective(BankTable record) {
        return bankTableMapper.insertSelective(record);
    }

    @Override
    public BankTable selectByPrimaryKey(Integer id) {
        return bankTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BankTable record) {
        return bankTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BankTable record) {
        return bankTableMapper.updateByPrimaryKey(record);
    }

}
