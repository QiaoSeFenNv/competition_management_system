package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCredits;
import com.qiaose.competitionmanagementsystem.entity.CompetitionReward;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CompetitionCreditsService;
import com.qiaose.competitionmanagementsystem.service.CompetitionRewardService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api("积分接口")
@RequestMapping("/credit")
public class CreditsController {


    @Autowired
    CompetitionCreditsService competitionCreditsService;

    @Autowired
    CompetitionRewardService competitionRewardService;



    @GetMapping("/getAllCredit")
    @ApiOperation(value = "获取所有内容",notes = "只需要传入page和pagesize即可")
    public R getAllCredit(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        //去出来的数据被包装了一次
        List<CompetitionCredits> credits = new ArrayList<>();
        PageHelper.startPage(page,pageSize);
        List<CompetitionCredits> list =  competitionCreditsService.getAllCredit();
        for (CompetitionCredits competitionCredits : list) {
            CompetitionReward competitionReward = competitionRewardService.selectByPrimaryKey(competitionCredits.getRewardId());
            competitionCredits.setReward_name( competitionReward.getRewardLevel() );
            credits.add(competitionCredits);
        }
        PageInfo<CompetitionCredits> pageInfo = new PageInfo<>(credits);
        List<CompetitionCredits> list1 = pageInfo.getList();
        PageDto pageDto = new PageDto();
        pageDto.setItems(list1);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }



    @GetMapping("/getCurCredit")
    @ApiOperation(value = "获取所有内容",notes = "只需要传入page和pagesize即可")
    public R getAllCredit(@RequestParam String recordLevelName
            ,@RequestParam Long recordRewardId){

        CompetitionCredits competitionCredits = competitionCreditsService.selectByNameAndId(recordRewardId,recordLevelName);

        return  R.ok(competitionCredits);
    }


    @PostMapping("/insertCredit")
    @ApiOperation(value = "插入一条积分等级信息" ,notes="传递一个对象，不需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R insertCredit(@RequestBody CompetitionCredits competitionCredits){
        if (competitionCredits == null) {
            return R.failed("数据为空");
        }
        competitionCredits.setCreateTime(DateKit.getNow());
        int i = competitionCreditsService.insertSelective(competitionCredits);

        if (i<=0){
            return R.failed("插入失败");
        }
        return R.ok("插入成功");
    }

    @PostMapping("/updateCredit")
    @ApiOperation(value = "更新一条数据" ,notes = "携带一条附带id值的对象")
    @Transactional(rollbackFor = {Exception.class})
    public R updateCredit(@RequestBody CompetitionCredits competitionCredits){

        if (competitionCredits == null){
            return R.failed("数据为空");
        }
        int i = competitionCreditsService.updateByPrimaryKeySelective(competitionCredits);

        if (i<=0){
            return R.failed("更新失败");
        }

        return R.ok("更新成功");
    }


    @PostMapping("/deleteCredit")
    @ApiOperation(value = "删除一条数据" ,notes = "携带d值的对象")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteCredit(@RequestBody CompetitionCredits competitionCredits){

        if (competitionCredits.getCreditId() == null){
            return R.failed("数据为空");
        }
        int i = competitionCreditsService.deleteByPrimaryKey(competitionCredits.getCreditId());

        if (i<=0){
            return R.failed("删除失败");
        }

        return R.ok("删除成功");
    }

}
