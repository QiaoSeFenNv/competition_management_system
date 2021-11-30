package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;

import com.qiaose.competitionmanagementsystem.entity.BankTable;
import com.qiaose.competitionmanagementsystem.entity.ClassTable;
import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
import com.qiaose.competitionmanagementsystem.entity.StudentInfo;

import com.qiaose.competitionmanagementsystem.service.BankTableService;
import com.qiaose.competitionmanagementsystem.service.ClassTableService;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.StudentInfoService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;

import com.qiaose.competitionmanagementsystem.utils.IDUtils;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@Api(value = "学生信息接口")
@RequestMapping("/student")
@Validated
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
//    @Transactional(rollbackFor = Exception.class)
    public R getCurStu(@RequestParam(required = false)  String stuId) {
        //根据学号查询学生信息对象
        StudentInfo studentInfo = studentInfoService.selectByStuId(stuId);
        if (studentInfo == null){
            return R.ok("无用户信息");
        }
        //用bankid查询银行西信息
        BankTable bankTable = bankTableService.selectByPrimaryKey(studentInfo.getBankId());
        studentInfo.setBankTable(bankTable);
        //用班级id查询班级信息
        ClassTable classTable = classTableService.selectByPrimaryKey(studentInfo.getClassId());
        studentInfo.setClassTable(classTable);
        //用college查询二级学院信息
        CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(studentInfo.getDeptId());
        studentInfo.setCollegeInfo(collegeInfo);

        return  R.ok(studentInfo);
    }



    @PostMapping("/insertCurStu")
    @ApiOperation(value="插入当前学生信息", notes="")
    @Transactional(rollbackFor = Exception.class)
    public R insertCurStu(@RequestBody @Valid StudentInfo studentInfo) {
        //从对象中取除 银行和班级对象，然后插入对应表中
        BankTable bankTable = new BankTable();
        bankTable = studentInfo.getBankTable();
        Integer bankId = IDUtils.getUUIDInOrderId();
        bankTable.setId(bankId);

        System.out.println(bankTable);

        ClassTable classTable = new ClassTable();
        classTable = studentInfo.getClassTable();
        Integer classId = IDUtils.getUUIDInOrderId();
        classTable.setClassId(classId);
        System.out.println(classTable);

        CollegeInfo collegeInfo = studentInfo.getCollegeInfo();
        System.out.println(collegeInfo);

        int i = bankTableService.insert(bankTable);
        int j = classTableService.insert(classTable);


        //查找college对应id号插入实体中
        CollegeInfo collegeInfo_id = collegeInfoService.selectByName(collegeInfo.getCollegeName());
        studentInfo.setDeptId(collegeInfo_id.getId());
        System.out.println(studentInfo);

        //插入整个对象
        studentInfo.setClassId(classId);
        studentInfo.setBankId(bankId);
        studentInfo.setCreateTime(DateKit.getNow());

        int k = studentInfoService.insertSelective(studentInfo);
        if ( i<=0 || j<=0 || k<=0) {
            return R.failed("插入失败");
        }
        return  R.ok("插入成功");
    }



    @PostMapping("/updateCurStu")
    @ApiOperation(value="更新当前学生信息", notes="携带参数需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R updateCurStu(@RequestBody @Valid StudentInfo studentInfo) {
        
        //查询当前对象
        StudentInfo studentInfo1 = studentInfoService.selectByPrimaryKey(studentInfo.getId());

        Integer bankId = studentInfo1.getBankId();
        Integer classId = studentInfo1.getClassId();
        Integer deptId = studentInfo1.getDeptId();
        //取出原本bankId
        BankTable bankTable = studentInfo.getBankTable();
        bankTable.setId(bankId);

        ClassTable classTable = studentInfo.getClassTable();
        classTable.setClassId(classId);

        studentInfo.setDeptId(deptId);
        studentInfo.setClassId(classId);
        studentInfo.setBankId(bankId);

        int k = studentInfoService.updateByPrimaryKeySelective(studentInfo);
        int i = bankTableService.updateByPrimaryKeySelective(bankTable);
        int j = classTableService.updateByPrimaryKeySelective(classTable);
        if(  j<=0 || i<=0 || k <= 0){
            return R.failed("更新失败");
        }

        return  R.ok("更新成功");
    }


    @PostMapping("/deleteCurStu")
    @ApiOperation(value="删除当前学生信息", notes="携带id")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCurStu(@RequestBody StudentInfo studentInfo) {
        BankTable bankTable = studentInfo.getBankTable();
        ClassTable classTable = studentInfo.getClassTable();
        bankTable.setUpdateTime(DateKit.getNowTime());
        classTable.setUpdateTime(DateKit.getNowTime());

        int i =studentInfoService.deleteByPrimaryKey(studentInfo.getId());
        int j = bankTableService.deleteByPrimaryKey(bankTable.getId());
        int k = classTableService.deleteByPrimaryKey(classTable.getClassId());

        if( i<=0 || j<=0 ||k <= 0){
            return R.failed("删除失败");
        }

        return  R.ok("删除成功");
    }


}
