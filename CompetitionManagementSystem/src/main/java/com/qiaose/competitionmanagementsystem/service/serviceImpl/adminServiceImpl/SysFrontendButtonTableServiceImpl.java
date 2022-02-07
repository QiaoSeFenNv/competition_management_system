package com.qiaose.competitionmanagementsystem.service.serviceImpl.adminServiceImpl;

import com.qiaose.competitionmanagementsystem.service.adminImpl.SysFrontendButtonTableService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.admin.SysFrontendButtonTable;
import com.qiaose.competitionmanagementsystem.mapper.adminMapper.SysFrontendButtonTableMapper;

import java.util.List;

@Service
public class SysFrontendButtonTableServiceImpl implements SysFrontendButtonTableService {

    @Resource
    private SysFrontendButtonTableMapper sysFrontendButtonTableMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysFrontendButtonTableMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysFrontendButtonTable record) {
        return sysFrontendButtonTableMapper.insert(record);
    }

    @Override
    public int insertSelective(SysFrontendButtonTable record) {
        return sysFrontendButtonTableMapper.insertSelective(record);
    }

    @Override
    public SysFrontendButtonTable selectByPrimaryKey(Long id) {
        return sysFrontendButtonTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysFrontendButtonTable record) {
        return sysFrontendButtonTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysFrontendButtonTable record) {
        return sysFrontendButtonTableMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysFrontendButtonTable> selectAll() {
        return sysFrontendButtonTableMapper.selectAll();
    }

    @Override
    public List<SysFrontendButtonTable> selectByName(String name) {
        return sysFrontendButtonTableMapper.selectByName(name);
    }

}
