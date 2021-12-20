package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CounselingInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CounselingInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("比赛报名")
@RequestMapping("/counseling")
public class CounselingController {

    @Autowired
    CounselingInfoService counselingInfoService;


    @GetMapping("/getAllInfo")
    @ApiOperation(value = "查询所有的辅导信息",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R getAllInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
            , @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        PageHelper.startPage(page,pageSize);
        List<CounselingInfo> counselingInfoList= counselingInfoService.selectAll();
        PageInfo<CounselingInfo> pageInfo = new PageInfo<>(counselingInfoList);
        List<CounselingInfo> list = pageInfo.getList();
        PageDto<CounselingInfo> pageDto = new PageDto<CounselingInfo>();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return R.ok(pageDto);
    }


    @PostMapping("/insertCounseling")
    @ApiOperation(value = "插入",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R insertCounseling(@RequestBody CounselingInfo counselingInfo){

        int i = counselingInfoService.insertSelective(counselingInfo);
        return R.ok("");
    }


    @PostMapping("/updateCounseling")
    @ApiOperation(value = "更新",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R updateCounseling(@RequestBody CounselingInfo counselingInfo){

        int i = counselingInfoService.updateByPrimaryKeySelective(counselingInfo);
        return R.ok("");
    }


    @GetMapping("/deleteCounseling")
    @ApiOperation(value = "删除",notes = "传递id号")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteCounseling(@RequestParam  Integer counselingId){
        int i = counselingInfoService.deleteByPrimaryKey(counselingId);
        return R.ok("");
    }
}
