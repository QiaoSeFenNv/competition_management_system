package com.qiaose.competitionmanagementsystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.api.R;

import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;

import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.service.CollegeInfoService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.utils.DateKit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "用户信息接口")
@RequestMapping("/userInfo")
@Validated
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    CollegeInfoService collegeInfoService;

    @Autowired
    SysRoleUserTableService sysRoleUserTableService;

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;


    @GetMapping("/getCurStu")
    @ApiOperation(value = "查询当前学生信息", notes = "返回数据为学生所有信息")
//    @Transactional(rollbackFor = Exception.class)
    public R getCurStu(@RequestParam(required = false) String workId) {
        //根据学号查询学生信息对象
        UserInfo userInfo = userInfoService.selectByWorkId(workId);
        if (userInfo == null) {
            return R.ok("无用户信息");
        }


        return R.ok(userInfo);
    }

    @PostMapping("/insertCurStu")
    @ApiOperation(value = "插入当前学生信息", notes = "传入对象")
    @Transactional(rollbackFor = Exception.class)
    public R insertCurStu(@RequestBody  UserInfo userInfo) {

        if (userInfo.getUserId()==null) {
            return R.failed("无学工号、无法生成");
        }

        //从对象中取除 银行和班级对象，然后插入对应表中
        insertOrgUser(userInfo);
        //插入整个对象
        userInfo.setCreateTime(DateKit.getNow());

        int k = userInfoService.insertSelective(userInfo);

        if ( k <= 0) {
            return R.failed("插入失败");
        }
        return R.ok("插入成功");
    }

    public void insertOrgUser(UserInfo userInfo){
        User user = new User();
        //名称
        user.setUserId(userInfo.getUserId());
        user.setUserPassword(bCryptPasswordEncoderUtil.encode("000000"));
        int j = userService.insertSelective(user);
        //绑定角色
        SysRoleUserTable sysRoleUserTable = new SysRoleUserTable();
        if (userInfo.getRole() == null){
            //默认为学生
            sysRoleUserTable.setRoleId("3");
            sysRoleUserTable.setUserId(userInfo.getUserId());
            sysRoleUserTableService.insertSelective(sysRoleUserTable);
        }else{
            for (String s : userInfo.getRole()) {
                sysRoleUserTable.setRoleId(s);
                sysRoleUserTable.setUserId(userInfo.getUserId());
                sysRoleUserTableService.insertSelective(sysRoleUserTable);
            }
        }
    }

    @PostMapping("/insertAll")
    @ApiOperation(value = "批量插入", notes = "excel表格")
    @Transactional(rollbackFor = Exception.class)
    public R insertAll(@RequestBody JSONArray userList){


        return R.ok("插入成功");
    }

//    @PostMapping("/insertAll")
//    @ApiOperation(value = "批量插入", notes = "excel表格")
//    @Transactional(rollbackFor = Exception.class)
//    public R insertAll(@RequestParam(name="file")MultipartFile file,@RequestParam String type) {
//        try {
//            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
//
//            Sheet sheet = wb.getSheetAt(0);
//
//            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
//                Row row = sheet.getRow(rowNum);//根据索引获取每一个行
//                Object [] values = new Object[row.getLastCellNum()];
//                for(int cellNum=1;cellNum< row.getLastCellNum(); cellNum++) {
//                    Cell cell = row.getCell(cellNum);
//                    Object value = getCellValue(cell);
//                    values[cellNum] = value;
//                }
//                UserInfo userInfo = new UserInfo(values);
////                insertOrgUser(userInfo);
//                userInfo.setCreateTime(DateKit.getNow());
//                int k = userInfoService.insertSelective(userInfo);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return R.ok("");
//    }

//    //格式装换
//    public static Object getCellValue(Cell cell) {
//        //1.获取到单元格的属性类型
//        CellType cellType = cell.getCellType();
//        //2.根据单元格数据类型获取数据
//        Object value = null;
//
//        switch (cellType) {
//            case STRING:
//                value = cell.getStringCellValue();
//                break;
//            case BOOLEAN:
//                value = cell.getBooleanCellValue();
//                break;
//            case NUMERIC:
//                if(DateUtil.isCellDateFormatted(cell)) {
//                    //日期格式
//                    value = cell.getDateCellValue();
//                }else{
//                    //数字
//                    value = cell.getNumericCellValue();
//                    value = value.toString();
//                }
//                break;
//            case FORMULA: //公式
//                value = cell.getCellFormula();
//                break;
//            default:
//                break;
//        }
//        return value;
//    }

    @PostMapping("/updateCurStu")
    @ApiOperation(value = "更新当前学生信息", notes = "携带参数需要携带id")
    @Transactional(rollbackFor = Exception.class)
    public R updateCurStu(@RequestBody UserInfo userInfo) {

        UserInfo userInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());
        User user = userService.selectByUserId(userInfo1.getUserId());
        List<SysRoleUserTable> sysRoleUserTables = sysRoleUserTableService.selectByUserId(userInfo1.getUserId());
        //更新的内容
        user.setUserId(userInfo.getUserId());
        for (SysRoleUserTable sysRoleUserTable : sysRoleUserTables) {
            sysRoleUserTable.setUserId(userInfo.getUserId());
            String[] role = userInfo.getRole();
            for (String s : role) {
                sysRoleUserTable.setRoleId(s);
                int i = sysRoleUserTableService.updateByPrimaryKeySelective(sysRoleUserTable);
            }
        }
        int k = userInfoService.updateByPrimaryKeySelective(userInfo);
        int i = userService.updateByPrimaryKeySelective(user);
        return R.ok("更新成功");
    }

    @PostMapping  ("/deleteCurStu")
    @ApiOperation(value = "删除当前学生信息", notes = "携带id")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCurStu(@RequestBody UserInfo userInfo) {

        UserInfo userInfo1 = userInfoService.selectByPrimaryKey(userInfo.getId());

        int i = userInfoService.deleteByPrimaryKey(userInfo1.getId());
        int k = userService.deleteByUserId(userInfo1.getUserId());
        int i1 = sysRoleUserTableService.deleteByUserId(userInfo1.getUserId());

        return R.ok("删除成功");
    }

}
