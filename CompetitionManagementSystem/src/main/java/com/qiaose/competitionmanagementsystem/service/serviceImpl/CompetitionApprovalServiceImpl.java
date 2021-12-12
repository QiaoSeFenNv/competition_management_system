package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.qiaose.competitionmanagementsystem.entity.UserInfo;
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

    @Override
    public CompetitionApproval SendApproval(Long approvalId, Long contentId, UserInfo userInfo,Long processId) {
        CompetitionApproval competitionApproval = new CompetitionApproval();
        //有些对象前端无法填写 申请表自动生成

        //该表的id
        competitionApproval.setApprovalId(approvalId);
        //申请的内容
        competitionApproval.setApplicantContentid(contentId);
        //申请的用户
        competitionApproval.setApplicantId(userInfo.getUserId());
        //申请用户的名称
        competitionApproval.setApplicantName(userInfo.getUserName());
        //创建初始状态都是执行中
        competitionApproval.setApprovalStatus((byte)0);
        //流程动态
        competitionApproval.setProcessId(processId);
        System.out.println(competitionApproval);
        return competitionApproval;

    }

}
