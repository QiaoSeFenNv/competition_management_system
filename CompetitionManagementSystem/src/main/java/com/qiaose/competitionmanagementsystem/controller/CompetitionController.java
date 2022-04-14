package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.qiaose.competitionmanagementsystem.entity.CompetitionInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;

import com.qiaose.competitionmanagementsystem.service.CompetitionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

@RestController
@Api("比赛信息")
@RequestMapping("/competition")
public class CompetitionController {

    @Resource
    CompetitionInfoService competitionInfoService;


    @GetMapping("/getAllInfo")
    @ApiOperation(value = "返回所有比赛",notes = "如果携带状态则根据比赛状态返回比赛信息，无携带状态返回所有")
    public R getAllInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
            , @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
            ,@RequestParam(required = false) Integer state){

        PageHelper.startPage(page,pageSize);
        List<CompetitionInfo> competitionInfoList;
        if (state!=null){
            competitionInfoList = competitionInfoService.selectByState(state);
        }else{
            competitionInfoList = competitionInfoService.selectByAll();
        }
        PageInfo<CompetitionInfo> pageInfo = new PageInfo<>(competitionInfoList);
        List<CompetitionInfo> list = pageInfo.getList();
        PageDto<CompetitionInfo> pageDto = new PageDto<CompetitionInfo>();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return R.ok(pageDto);
    }



    @GetMapping("/getInfoById")
    @ApiOperation(value = "返回比赛",notes = "根据id号进行查询")
    public R getInfoById(@RequestParam(required = true) Long id){
        CompetitionInfo competitionInfo = competitionInfoService.selectByPrimaryKey(id);
        if (competitionInfo == null){
            return R.failed("无对应id");
        }

        return R.ok(competitionInfo);
    }

    @GetMapping("/getInfoByName")
    @ApiOperation(value = "模糊返回比赛",notes = "根据name进行查询")
    public R getInfoById(@RequestParam(required = false, defaultValue = "") String name){
        List<CompetitionInfo> competitionInfo = competitionInfoService.selectByName(name);
        if (competitionInfo == null){
            return R.failed("无对应id");
        }

        return R.ok(competitionInfo);
    }


    @PostMapping("/insertInfo")
    @ApiOperation(value = "插入比赛信息",notes = "需要body")
    @Transactional(rollbackFor = {Exception.class})
    public R insertInfo(@RequestBody CompetitionInfo competitionInfo){

        int i = competitionInfoService.insertSelective(competitionInfo);
        if (i<=0){
            return R.failed("");
        }
        return R.ok("");
    }

    @PostMapping("/updateInfo")
    @ApiOperation(value = "更新比赛信息",notes = "需要body，携带id值")
    @Transactional(rollbackFor = {Exception.class})
    public R updateInfo(@RequestBody CompetitionInfo competitionInfo){

        int i = competitionInfoService.updateByPrimaryKeySelective(competitionInfo);
        if (i<=0){
            return R.failed("");
        }
        return R.ok("");
    }

    @GetMapping("/deleteInfo")
    @ApiOperation(value = "删除比赛信息",notes = "get请求删除比赛信息")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteInfo(@RequestParam Long id){
        int i = competitionInfoService.deleteByPrimaryKey(id);
        if (i<=0){
            return R.failed("");
        }
        return R.ok("");
    }

}
