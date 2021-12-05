package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import com.qiaose.competitionmanagementsystem.service.CompetitionRecordService;
import com.qiaose.competitionmanagementsystem.utils.IDUtils;
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



}
