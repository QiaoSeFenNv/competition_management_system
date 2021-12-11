//package com.qiaose.competitionmanagementsystem.controller;
//
//import com.baomidou.mybatisplus.extension.api.R;
//
//import com.qiaose.competitionmanagementsystem.entity.BankTable;
//
//import com.qiaose.competitionmanagementsystem.entity.CollegeInfo;
//import com.qiaose.competitionmanagementsystem.entity.UserInfo;
//import com.qiaose.competitionmanagementsystem.service.BankTableService;
//import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
//import com.qiaose.competitionmanagementsystem.service.UserInfoService;
//import com.qiaose.competitionmanagementsystem.utils.DateKit;
//
//import com.qiaose.competitionmanagementsystem.utils.IDUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//
//@RestController
//@Api(value = "用户信息接口")
//@RequestMapping("/userInfo")
//@Validated
//public class UserInfoController {
//
//    @Autowired
//    UserInfoService userInfoService;
//
//    @Autowired
//    CollegeInfoService collegeInfoService;
//
//    @Resource
//    BankTableService bankTableService;
//
//
//
//    @GetMapping("/getCurStu")
//    @ApiOperation(value = "查询当前学生信息", notes = "返回数据为学生所有信息")
////    @Transactional(rollbackFor = Exception.class)
//    public R getCurStu(@RequestParam(required = false) String stuId) {
//        //根据学号查询学生信息对象
//        UserInfo userInfo = userInfoService.selectByStuId(stuId);
//        if (userInfo == null) {
//            return R.ok("无用户信息");
//        }
//        //用bankid查询银行西信息
//        BankTable bankTable = bankTableService.selectByPrimaryKey(userInfo.getBankId());
//        userInfo.setBankTable(bankTable);
//        //用班级id查询班级信息
//        ClassTable classTable = classTableService.selectByPrimaryKey(Long.valueOf(userInfo.getClassId()));
//        userInfo.setClassTable(classTable);
//        //用college查询二级学院信息
//        CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(userInfo.getDeptId());
//        userInfo.setCollegeInfo(collegeInfo);
//
//        return R.ok(userInfo);
//    }
//
//    @PostMapping("/insertCurStu")
//    @ApiOperation(value = "插入当前学生信息", notes = "传入对象")
//    @Transactional(rollbackFor = Exception.class)
//    public R insertCurStu(@RequestBody @Valid UserInfo userInfo) {
//        //从对象中取除 银行和班级对象，然后插入对应表中
//
//        BankTable bankTable = userInfo.getBankTable();
//        Integer bankId = IDUtils.getUUIDInOrderId();
//        bankTable.setId(bankId);
//
//
//        Integer classId = IDUtils.getUUIDInOrderId();
//
//
//        CollegeInfo collegeInfo = userInfo.getCollegeInfo();
//
//        int i = bankTableService.insert(bankTable);
//
//
//        //查找college对应id号插入实体中
//        CollegeInfo collegeInfo_id = collegeInfoService.selectByName(collegeInfo.getCollegeName());
//
//        System.out.println(userInfo);
//
//        //插入整个对象
//        userInfo.setBankId(bankId);
//        userInfo.setCreateTime(DateKit.getNow());
//
//        int k = userInfoService.insertSelective(userInfo);
//        if (i <= 0 || j <= 0 || k <= 0) {
//            return R.failed("插入失败");
//        }
//        return R.ok("插入成功");
//    }
//
//    @PostMapping("/updateCurStu")
//    @ApiOperation(value = "更新当前学生信息", notes = "携带参数需要携带id")
//    @Transactional(rollbackFor = Exception.class)
//    public R updateCurStu(@RequestBody @Valid UserInfo userInfo) {
//
//        //查询当前对象
//        UserInfo studentInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());
//
//        //由于studentinfo中没有携带三个id，自己获取
//        Integer bankId = studentInfo1.getBankId();
//        System.out.println(bankId);
//        Integer classId = studentInfo1.getClassId();
//        System.out.println(classId);
//
//        //取出原本三个对象，在为三个对象赋id值进行更新
//        BankTable bankTable = userInfo.getBankTable();
//        bankTable.setId(bankId);
//
//        classTable.setClassId(Long.valueOf(classId));
//
//        userInfo.setDeptId(deptId);
//        userInfo.setBankId(bankId);
//
//        int k = userInfoService.updateByPrimaryKeySelective(userInfo);
//
//        return R.ok("更新成功");
//    }
//
//    @PostMapping("/deleteCurStu")
//    @ApiOperation(value = "删除当前学生信息", notes = "携带id")
//    @Transactional(rollbackFor = Exception.class)
//    public R deleteCurStu(@RequestBody UserInfo userInfo) {
//
//        UserInfo studentInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());
//        System.out.println(studentInfo1);
//        Integer bankId = studentInfo1.getBankId();
//
//
//
//        int i = userInfoService.deleteByPrimaryKey(userInfo.getId());
//        int j = bankTableService.deleteByPrimaryKey(bankId);
//
//
//
//        return R.ok("删除成功");
//    }
//
//}
