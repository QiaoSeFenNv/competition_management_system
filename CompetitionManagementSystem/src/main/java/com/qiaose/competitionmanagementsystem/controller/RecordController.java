package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.service.CompetitionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="赛事信息记录接口")
@RequestMapping("/record")
public class RecordController {


    @Autowired
    CompetitionRecordService competitionRecordService;


    @GetMapping("/getAllRecord")
    @ApiOperation(value = "显示所有记录信息",notes = "给管理员查看每条比赛记录")
    public R getAllRecord(){

        List<CompetitionRecord> competitionRecords = competitionRecordService.selectByAll();
        return R.ok("success");
    }

}
