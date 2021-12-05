package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.service.CompetitionApprovalService;
import com.qiaose.competitionmanagementsystem.service.CompetitionRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("比赛记录接口")
@RequestMapping("/record")
public class RecordController {




    @Autowired
    CompetitionRecordService competitionRecordService;


    @Autowired
    CompetitionApprovalService competitionApprovalService;


    @GetMapping("/curRecord")
    public R  curRecord(@RequestParam Long approvalId){
        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(approvalId);

        if (competitionApproval == null)
            return R.failed("");

        CompetitionRecord competitionRecord = competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid());

        return R.ok(competitionRecord);

    }



}
