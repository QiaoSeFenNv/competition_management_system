package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.qiaose.competitionmanagementsystem.entity.CompetitionOrganizer;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CompetitionOrganizerService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("积分接口")
@RequestMapping("/organize")
public class OrganizeController {


    @Autowired
    CompetitionOrganizerService competitionOrganizerService;



    @GetMapping("/getAllOrganize")
    @ApiOperation(value = "获得所有比赛组织信息")
    public R getAllOrganize(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {

        PageHelper.startPage(page,pageSize);

        List<CompetitionOrganizer> list= competitionOrganizerService.selectAll();

        PageInfo<CompetitionOrganizer> pageInfo = new PageInfo<>(list);
        List<CompetitionOrganizer> list1 = pageInfo.getList();

        PageDto<CompetitionOrganizer> competitionOrganizerPageDto = new PageDto<>();

        competitionOrganizerPageDto.setItems(list1);
        competitionOrganizerPageDto.setTotal((int)pageInfo.getTotal());

        return R.ok(competitionOrganizerPageDto);
    }

    @GetMapping("/findOrganize")
    @ApiOperation(value = "模糊查询",notes = "传入三个参数进行模糊查询")
    public R findOrganize(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
            ,@RequestParam(required = false) String organize) {
        PageHelper.startPage(page,pageSize);

        List<CompetitionOrganizer> list= competitionOrganizerService.findOrganize(organize);

        PageInfo<CompetitionOrganizer> pageInfo = new PageInfo<>(list);
        List<CompetitionOrganizer> list1 = pageInfo.getList();

        PageDto<CompetitionOrganizer> PageDto = new PageDto<>();

        PageDto.setItems(list1);
        PageDto.setTotal((int)pageInfo.getTotal());

        return R.ok(PageDto);
    }




    @PostMapping("/insertOrganize")
    @ApiOperation(value = "插入比赛名称表",notes = "传入一个body 不需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R insertOrganize(@RequestBody CompetitionOrganizer competitionOrganizer) {

        if (competitionOrganizer == null) {
            return R.failed("数据不全");
        }
        competitionOrganizer.setCreateTime(DateKit.getNow());
        int i = competitionOrganizerService.insertSelective(competitionOrganizer);

        if (i<=0) {
            return R.failed("插入失败");
        }

        return R.ok("插入成功");
    }

    @PostMapping("/updateOrganize")
    @ApiOperation(value = "更新比赛名称表",notes = "传入一个body 需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R updateOrganize(@RequestBody CompetitionOrganizer competitionOrganizer) {

        if (competitionOrganizer == null) {
            return R.failed("数据不全");
        }

        int i = competitionOrganizerService.updateByPrimaryKeySelective(competitionOrganizer);

        if (i<=0) {
            return R.failed("更新失败");
        }

        return R.ok("更新成功");
    }


    @PostMapping("/deleteOrganize")
    @ApiOperation(value = "删除比赛名称表",notes = "需要携带id")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteOrganize(@RequestBody CompetitionOrganizer competitionOrganizer) {

        if (competitionOrganizer == null) {
            return R.failed("数据不全");
        }

        int i = competitionOrganizerService.deleteByPrimaryKey(competitionOrganizer.getOrganizeId());

        if (i<=0) {
            return R.failed("删除失败");
        }

        return R.ok("删除成功");
    }


}
