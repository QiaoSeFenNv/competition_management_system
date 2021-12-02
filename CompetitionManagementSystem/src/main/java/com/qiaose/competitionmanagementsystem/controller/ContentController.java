package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CompetitionContents;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;

import com.qiaose.competitionmanagementsystem.service.CompetitionContentsService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "文章接口")
@RequestMapping("/content")
public class ContentController {

    @Autowired
    CompetitionContentsService competitionContentsService;



    @GetMapping("/getContents")
    @ApiOperation(value = "返回所有文章")
    public R getContents(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        PageHelper.startPage(page,pageSize);
        List<CompetitionContents> competitionContents = competitionContentsService.selectALl();
        PageInfo<CompetitionContents> pageInfo = new PageInfo<>(competitionContents);
        List<CompetitionContents> list = pageInfo.getList();
        PageDto pageDto = new PageDto();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());

        return R.ok(pageDto);
    }

    @PostMapping("/getContent")
    @ApiOperation(value = "获得选择文章",notes = "输入id值即可")
    public R getContent(@RequestBody CompetitionContents competitionContents){
        competitionContents.getId()
        CompetitionContents competitionContents = competitionContentsService.selectByPrimaryKey(id);
        if (competitionContents == null){
            return R.failed("失败");
        }
        return R.ok(competitionContents);
    }




    @PostMapping("/insertContents")
    @ApiOperation(value = "插入文章",notes = "需要body")
    @Transactional(rollbackFor = {Exception.class})
    public R insertContents(@RequestBody CompetitionContents competitionContents){

        competitionContents.setCreateTime(DateKit.getNow());
        int i = competitionContentsService.insertSelective(competitionContents);

        if (i<=0){
            return R.failed("");
        }

        return R.ok("");
    }

    @GetMapping("/deleteContents")
    @ApiOperation(value = "删除文章",notes = "需要id")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteContents(@RequestParam(required = false,value = "id")Integer id){

        int i = competitionContentsService.deleteByPrimaryKey(id);

        if (i<=0){
            return R.failed("");
        }

        return R.ok("");
    }


    @PostMapping("/updateContents")
    @ApiOperation(value = "更新文章",notes = "需要body和id")
    @Transactional(rollbackFor = {Exception.class})
    public R updateContents(@RequestBody CompetitionContents competitionContents){

        competitionContents.setUpdateTime(DateKit.getNow());
        int i = competitionContentsService.updateByPrimaryKeySelective(competitionContents);

        if (i<=0){
            return R.failed("");
        }

        return R.ok("");
    }
}
