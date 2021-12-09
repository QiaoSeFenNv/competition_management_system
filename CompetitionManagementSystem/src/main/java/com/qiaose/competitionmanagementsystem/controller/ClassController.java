package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CollegeClassTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CollegeClassTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("班级表")
@RequestMapping("/approval")
public class ClassController {


    @Autowired
    CollegeClassTableService collegeClassTableService;


    @GetMapping("getAllClass")
    @ApiOperation(value="返回所有的班级",notes="")
    public R getAllClass(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        PageHelper.startPage(page,pageSize);
        List<CollegeClassTable> list= collegeClassTableService.getAllClass();

        PageInfo<CollegeClassTable> pageInfo = new PageInfo<>(list);
        List<CollegeClassTable> list1 = pageInfo.getList();
        PageDto pageDto = new PageDto();
        pageDto.setItems(list1);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }





}
