package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionApprovalMapper;
import com.qiaose.competitionmanagementsystem.service.CompetitionApprovalService;
@Service
public class CompetitionApprovalServiceImpl implements CompetitionApprovalService{

    @Resource
    private CompetitionApprovalMapper competitionApprovalMapper;

    @Override
    public int deleteByPrimaryKey(Long approvalId) {
        return competitionApprovalMapper.deleteByPrimaryKey(approvalId);
    }

    @Override
    public int insert(CompetitionApproval record) {
        return competitionApprovalMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionApproval record) {
        return competitionApprovalMapper.insertSelective(record);
    }

    @Override
    public CompetitionApproval selectByPrimaryKey(Long approvalId) {
        return competitionApprovalMapper.selectByPrimaryKey(approvalId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionApproval record) {
        return competitionApprovalMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionApproval record) {
        return competitionApprovalMapper.updateByPrimaryKey(record);
    }

}
