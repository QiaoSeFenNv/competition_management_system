package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.service.CompetitionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("学生申请表接口")
@RequestMapping("/record")
public class RecordController {

    @Autowired
    CompetitionRecordService competitionRecordService;



    @PostMapping("/insertRecord")
    @ApiOperation(value = "插入比赛名称表",notes = "传入一个body")
    @Transactional(rollbackFor = {Exception.class})
    public R insertRecord(@RequestBody CompetitionRecord competitionRecord){

        if (competitionRecord == null) {
            return R.failed("数据为空");
        }

        int i = competitionRecordService.insertSelective(competitionRecord);
        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }

}
