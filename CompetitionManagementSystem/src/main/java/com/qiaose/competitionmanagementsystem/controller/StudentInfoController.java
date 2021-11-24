package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.StudentInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysUserDto;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "学生信息接口")
@RequestMapping("/student")
public class StudentInfoController {


    @Autowired
    StudentInfoService studentInfoService;

    @Autowired
    CollegeInfoService collegeInfoService;



    @GetMapping("/getAllStu")
    @ApiOperation(value="查询所有的学生信息", notes="未完成")
    public R getCurStuInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
            , @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {


        PageHelper.startPage(page,pageSize);
        List<StudentInfo> studentInfo = studentInfoService.selectByAll();
        PageInfo<StudentInfo> pageInfo = new PageInfo<>(studentInfo);
        List<StudentInfo> list = pageInfo.getList();

        //未完成
        
        
        PageDto pageDto = new PageDto();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }

}
