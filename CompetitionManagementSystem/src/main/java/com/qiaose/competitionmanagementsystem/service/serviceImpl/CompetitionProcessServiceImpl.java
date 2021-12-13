package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.CompetitionProcessMapper;
import com.qiaose.competitionmanagementsystem.entity.CompetitionProcess;
import com.qiaose.competitionmanagementsystem.service.CompetitionProcessService;
@Service
public class CompetitionProcessServiceImpl implements CompetitionProcessService{

    @Resource
    private CompetitionProcessMapper competitionProcessMapper;

    @Resource
    CollegeInfoService collegeInfoService;

    @Override
    public int deleteByPrimaryKey(Long processId) {
        return competitionProcessMapper.deleteByPrimaryKey(processId);
    }

    @Override
    public int insert(CompetitionProcess record) {
        return competitionProcessMapper.insert(record);
    }

    @Override
    public int insertSelective(CompetitionProcess record) {
        return competitionProcessMapper.insertSelective(record);
    }

    @Override
    public CompetitionProcess selectByPrimaryKey(Long processId) {
        return competitionProcessMapper.selectByPrimaryKey(processId);
    }

    @Override
    public int updateByPrimaryKeySelective(CompetitionProcess record) {
        return competitionProcessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CompetitionProcess record) {
        return competitionProcessMapper.updateByPrimaryKey(record);
    }

    @Override
    public String passProcess(Long processId, UserInfo userInfo)  {

        CompetitionProcess competitionProcess = competitionProcessMapper.selectByPrimaryKey(processId);
        System.out.println("这个发放进来了");
        //修改之前事务表的拥有者
        if ("@FDY".equals(competitionProcess.getApproverId() )){
            System.out.println("FDY进来了没啊？");
            CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(Integer.valueOf(userInfo.getDeptId()));
            if (collegeInfo.getDutyId()==null) {
                return null;
            }else{
                return collegeInfo.getDutyId();
            }
        }
        if ("@ERXY".equals(competitionProcess.getApproverId() )){
            System.out.println("EJXU进来了没啊？");
            CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(Integer.valueOf(userInfo.getDeptId()));
            String[] split = collegeInfo.getAncestors().split(",");
            String result = "";
            for (int i1 = 0; i1 < split.length; i1++) {
                if (i1==2) {
                    result = split[i1];
                }
            }
            System.out.println(result);
            CollegeInfo collegeInfo1 = collegeInfoService.selectByPrimaryKey(Integer.valueOf(result));
            if (collegeInfo1.getDutyId()==null) {
                return null;
            }else{
                return collegeInfo1.getDutyId();
            }
        }


        return competitionProcess.getApproverId();
    }

}
