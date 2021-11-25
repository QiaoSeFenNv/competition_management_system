package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.entity.BankTable;
import com.qiaose.competitionmanagementsystem.entity.ClassTable;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.StudentInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysUserDto;
import com.qiaose.competitionmanagementsystem.service.BankTableService;
import com.qiaose.competitionmanagementsystem.service.ClassTableService;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    BankTableService bankTableService;

    @Resource
    ClassTableService classTableService;



    @GetMapping("/getCurStu")
    @ApiOperation(value="查询当前学生信息", notes="返回数据为学生所有信息")
    public R getCurStu(@RequestParam String stuId) {
        //根据学号查询学生信息对象
        StudentInfo studentInfo = studentInfoService.selectByStuId(stuId);
        //用银行id号查询银行信息
        BankTable bankTable = bankTableService.selectByPrimaryKey(studentInfo.getBankId());
        studentInfo.setBankTable(bankTable);
        //用班级id查询班级信息
        ClassTable classTable = classTableService.selectByPrimaryKey(studentInfo.getClassId());
        studentInfo.setClassTable(classTable);

        return  R.ok(studentInfo);
    }

    @PostMapping("/insertCurStu")
    @ApiOperation(value="插入当前学生信息", notes="携带参数不需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R insertCurStu(@RequestBody StudentInfo studentInfo) {
        BankTable bankTable = studentInfo.getBankTable();
        ClassTable classTable = studentInfo.getClassTable();

        int i = bankTableService.insertSelective(bankTable);
        int j = classTableService.insertSelective(classTable);

        studentInfo.setClassId(classTable.getClassId());

        int k = studentInfoService.insertSelective(studentInfo);
        if ( j<=0 || j<=0 || k<=0) {

            return R.failed("插入失败");
        }
        return  R.ok("插入成功");
    }

    @PostMapping("/updateCurStu")
    @ApiOperation(value="更新当前学生信息", notes="携带参数需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R updateCurStu(@RequestBody StudentInfo studentInfo) {
        BankTable bankTable = studentInfo.getBankTable();
        ClassTable classTable = studentInfo.getClassTable();

        int i = bankTableService.insertSelective(bankTable);
        int j = classTableService.insertSelective(classTable);

        studentInfo.setClassId(classTable.getClassId());

        int k = studentInfoService.insertSelective(studentInfo);
        if ( j<=0 || j<=0 || k<=0) {

            return R.failed("插入失败");
        }
        return  R.ok("插入成功");
    }


}
