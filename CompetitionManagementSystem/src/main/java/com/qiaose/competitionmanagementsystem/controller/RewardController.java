package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CompetitionReward;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CompetitionRewardService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api("等级接口")
@RequestMapping("/reward")
public class RewardController {

    @Autowired
    CompetitionRewardService competitionRewardService;




    @GetMapping("/getAllReward")
    @ApiOperation(value = "获取所有的等级")
    public R getAllReward(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        PageHelper.startPage(page,pageSize);

        List<CompetitionReward> list = competitionRewardService.selectAll();

        PageInfo<CompetitionReward> pageInfo = new PageInfo(list);

        List<CompetitionReward> list1 = pageInfo.getList();

        PageDto pageDto = new PageDto();
        pageDto.setItems(list1);
        pageDto.setTotal((int)pageInfo.getTotal());
        return R.ok(pageDto);
    }


    @GetMapping("/getRewardById")
    @ApiOperation(value = "获取所有的等级",notes = "需要携带一个id值")
    public R getReward(@RequestParam Long id){

        if (id == null) {
            return R.failed("");
        }
        CompetitionReward competitionReward = competitionRewardService.selectByPrimaryKey(id);

        return R.ok(competitionReward);
    }



    @PostMapping("/insertReward")
    @ApiOperation(value = "插入等级",notes = "需要插入一个对象，不需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R insertReward(@RequestBody CompetitionReward competitionReward){

        if (competitionReward == null) {
            return R.failed("");
        }
        competitionReward.setCreateTime(DateKit.getNow());
        int i = competitionRewardService.insertSelective(competitionReward);

        if (i<=0) {
            return R.failed("插入失败");
        }

        return R.ok("插入成功");
    }


    @PostMapping("/updateReward")
    @ApiOperation(value = "更新等级",notes = "需要插入一个对象，需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R updateReward(@RequestBody CompetitionReward competitionReward){

        if (competitionReward.getRewardId() == null) {
            return R.failed("");
        }
        competitionReward.setUpdateTime(new Date());
        int i = competitionRewardService.updateByPrimaryKey(competitionReward);

        if (i<=0) {
            return R.failed("更新失败");
        }

        return R.ok("更新成功");
    }


    @PostMapping("/deleteReward")
    @ApiOperation(value = "删除等级",notes = "需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteReward(@RequestBody CompetitionReward competitionReward){

        if (competitionReward.getRewardId() == null) {
            return R.failed("");
        }

        int i = competitionRewardService.deleteByPrimaryKey(competitionReward.getRewardId());

        if (i<=0) {
            return R.failed("删除失败");
        }

        return R.ok("删除成功");
    }
}
