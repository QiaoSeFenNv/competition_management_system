package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.CounselingInfo;
import com.qiaose.competitionmanagementsystem.mapper.CounselingInfoMapper;
import com.qiaose.competitionmanagementsystem.service.CounselingInfoService;

import java.util.List;

@Service
public class CounselingInfoServiceImpl implements CounselingInfoService{

    @Resource
    private CounselingInfoMapper counselingInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return counselingInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CounselingInfo record) {
        return counselingInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(CounselingInfo record) {
        return counselingInfoMapper.insertSelective(record);
    }

    @Override
    public CounselingInfo selectByPrimaryKey(Integer id) {
        return counselingInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CounselingInfo record) {
        return counselingInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CounselingInfo record) {
        return counselingInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CounselingInfo> selectAll() {
        return counselingInfoMapper.selectAll();
    }

}
