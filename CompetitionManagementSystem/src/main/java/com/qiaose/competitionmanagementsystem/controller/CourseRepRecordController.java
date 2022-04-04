package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCourseRepRecord;
import com.qiaose.competitionmanagementsystem.entity.CompetitionPrice;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.PriceDto;
import com.qiaose.competitionmanagementsystem.entity.dto.StudentDto;
import com.qiaose.competitionmanagementsystem.exception.TipException;
import com.qiaose.competitionmanagementsystem.service.ICompetitionCourseRepRecordService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.serviceImpl.CompetitionCourseRepRecordServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CourseRepRecordController
 * @Description:
 * @Author qiaosefennv
 * @Date 2022/4/3
 * @Version 1.0
 */

@RestController
@Slf4j
@Api("积分接口")
@RequestMapping("/repRecord")
public class CourseRepRecordController {


    private  ICompetitionCourseRepRecordService icompetitionCourseRepRecordService;

    @Autowired
    public CourseRepRecordController(ICompetitionCourseRepRecordService icompetitionCourseRepRecordService) {
        this.icompetitionCourseRepRecordService = icompetitionCourseRepRecordService;
    }

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @GetMapping("/getAllRepRecord")
    @ApiOperation(value = "获取所有学分申请记录")
    public R getAllRepRecord(@RequestParam(defaultValue = "1", value = "page") Integer pageNo
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){

        //查询所有信息
        QueryWrapper<CompetitionCourseRepRecord> queryWrapper = new QueryWrapper<>();
        Page<CompetitionCourseRepRecord> page = new Page<>(pageNo, pageSize);
        IPage<CompetitionCourseRepRecord> pageList = icompetitionCourseRepRecordService.page(page, queryWrapper);

        PageDto<CompetitionCourseRepRecord> pageDto = new PageDto<>();
        pageDto.setTotal((int) pageList.getTotal());
        pageDto.setItems(pageList.getRecords());

        return R.ok(pageDto);
    }

    @GetMapping("/getRepRecord")
    @ApiOperation(value = "获取当前学分学分申请记录")
    public R getRepRecord(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        System.out.println(token);
        //
        String username = jwtTokenUtil.getUsernameFromToken(token);

        UserInfo user = userInfoService.selectByWorkId(username);

        QueryWrapper<CompetitionCourseRepRecord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",user.getUserId());
        List<CompetitionCourseRepRecord> list = icompetitionCourseRepRecordService.list(queryWrapper);

        return R.ok(list);
    }

    @PostMapping("addRepRecord")
    @ApiOperation(value = "添加一条学分申请记录")
    @Transactional(rollbackFor = {TipException.class,Exception.class})
    public R addRepRecord(@RequestBody CompetitionCourseRepRecord competitionCourseRepRecord,HttpServletRequest request, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("用户信息：{}",userDetails.getUsername());
        competitionCourseRepRecord.setUserId(userDetails.getUsername());
        competitionCourseRepRecord.setStatus(0);

        String token = request.getHeader("Authorization");

        System.out.println(token);
        //
        String username = jwtTokenUtil.getUsernameFromToken(token);

        UserInfo user = userInfoService.selectByWorkId(username);
        if (user.getCreditsRemain() == 0 && user.getCreditsRemain()<competitionCourseRepRecord.getCreditUsed()) {
            throw new TipException("剩余学分不够进行置换");
        }

        boolean save = icompetitionCourseRepRecordService.save(competitionCourseRepRecord);
        return R.ok(save);
    }

    @DeleteMapping ("deleteRepRecord")
    @ApiOperation(value = "删除一条学分申请记录")
    public R deleteRepRecord(@RequestBody List<Integer> ids){
        boolean save = icompetitionCourseRepRecordService.removeByIds(ids);
        return R.ok(save);
    }

    @PutMapping("updateRepRecord")
    @ApiOperation(value = "更新一条学分申请记录")
    public R updateRepRecord(@RequestBody CompetitionCourseRepRecord competitionCourseRepRecord){
        boolean save = icompetitionCourseRepRecordService.updateById(competitionCourseRepRecord);
        return R.ok(save);
    }
}
