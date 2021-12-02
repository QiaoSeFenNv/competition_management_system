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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "二级学院接口")
@RequestMapping("/college")
public class CollegeInfoController {

    @Autowired
    CollegeInfoService collegeInfoService;

    @Autowired
    StudentInfoService studentInfoService;




    @GetMapping("/getAllDeptInfo")
    @ApiOperation(value="查询所有二级学院", notes="显示所有二级学院")
    public R getAllDeptInfo(@RequestParam(required = false ,value = "collegeName") String collegeName ) {


        List<CollegeInfo> collegeInfos = collegeInfoService.findByName(collegeName);

        return  R.ok(collegeInfos);
    }



    /**
     * 查询二级学院对应的学生信息
     * @param page
     * @param pageSize
     * @param deptId
     * @return
     */
    @GetMapping("/getCurStuInfo")
    @ApiOperation(value="查询二级学院对应的学生信息", notes="携带三个参数")
    public R getCurStuInfo(@RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize
            ,@RequestParam(required = false,value = "deptId") Integer deptId) {


        List<SysUserDto> listUser = new ArrayList<>();
        PageHelper.startPage(page,pageSize);
        List<StudentInfo> studentInfo = null;
        if (deptId == null){
            studentInfo = studentInfoService.selectByAll();
            //将student封装成为SysUser
            for (StudentInfo info : studentInfo) {
                CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(info.getDeptId());
                SysUserDto sysUserDto = StuChangeUser(info);
                sysUserDto.setDepartment(collegeInfo.getCollegeName());
                listUser.add(sysUserDto);
            }
        }else{
            studentInfo = studentInfoService.selectByDeptId(deptId);
            CollegeInfo collegeInfo = collegeInfoService.selectByPrimaryKey(deptId);
            //将student封装成为SysUser
            for (StudentInfo info : studentInfo) {
                SysUserDto sysUserDto = StuChangeUser(info);
                sysUserDto.setDepartment(collegeInfo.getCollegeName());
                listUser.add(sysUserDto);
            }
        }

        PageInfo<SysUserDto> pageInfo = new PageInfo<>(listUser);

        List<SysUserDto> list = pageInfo.getList();

        PageDto pageDto = new PageDto();
        pageDto.setItems(list);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);
    }




    @PostMapping("/insertDeptInfo")
    @ApiOperation(value="插入二级学院", notes="前端携带body")
    public R insertDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        //判断是否有重复字段
        List<CollegeInfo> collegeInfos = collegeInfoService.selectAll();
        for (CollegeInfo info : collegeInfos) {
            if (collegeInfo.getId() != info.getId() && info.getCollegeName() == collegeInfo.getCollegeName() ){
                return R.failed("字段重复");
            }
        }

        int i = collegeInfoService.insertSelective(collegeInfo);

        if ( i <= 0){
            return R.failed("插入失败");
        }

        return  R.ok("插入成功");
    }


    @PostMapping("/updateDeptInfo")
    @ApiOperation(value="更新二级学院", notes="请求body里面要携带id")
    public R updateDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        int i = collegeInfoService.updateByPrimaryKeySelective(collegeInfo);
        if ( i <= 0){
            return R.failed("更新失败");
        }
        return  R.ok("更新成功");
    }



    @PostMapping("/deleteDeptInfo")
    @ApiOperation(value="删除二级学院", notes="请求body只携带id")
    public R deleteDeptInfo(@RequestBody CollegeInfo collegeInfo) {

        int i = collegeInfoService.deleteByPrimaryKey(collegeInfo.getId());
        if ( i <= 0){
            return R.failed("删除失败");
        }
        return  R.ok("删除成功");
    }





    public SysUserDto StuChangeUser(StudentInfo studentInfo){
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setStuId(studentInfo.getStuId());
        sysUserDto.setPhone(studentInfo.getTelephone());
        sysUserDto.setEmail(studentInfo.getEmail());
        sysUserDto.setName(studentInfo.getName());
        return sysUserDto;
    }


}
