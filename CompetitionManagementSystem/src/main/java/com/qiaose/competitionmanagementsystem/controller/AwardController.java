package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.CompetitionApply;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.service.CompetitionApplyService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api("比赛报名")
@RequestMapping("/award")
public class AwardController {

    @Autowired
    CompetitionApplyService competitionApplyService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;


    @PostMapping("/insertAward")
    @ApiOperation(value = "插入",notes = "未完整,雏形初现再问王苹")
    @Transactional(rollbackFor = {Exception.class})
    public R insertApply(@RequestBody CompetitionApply competitionApply){

        String[] applyStudents = competitionApply.getApplystudents();

        String applyStudent =  changInsert(applyStudents);

        competitionApply.setApplystudent(applyStudent);

        int i = competitionApplyService.insertSelective(competitionApply);

        return R.ok("");
    }

    public String changInsert(String[] applystudents){
        String result = "";
        for (String applystudent : applystudents) {
            result += ","+applystudent;
        }
        result= result.substring(1);
        return result;
    }


    @GetMapping("/getAward")
    @ApiOperation(value = "获得自己的报名信息",notes = "未完整,雏形初现再问王苹")
    @Transactional(rollbackFor = {Exception.class})
    public R getApply(HttpServletRequest request, @RequestParam(defaultValue = "1", value = "page") Integer page
            ,@RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize){
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByUserId(username);

        PageHelper.startPage(page,pageSize);
        List<CompetitionApply> competitionApplies = competitionApplyService.selectByUserId(user.getUserId());

        PageInfo<CompetitionApply> pageInfo = new PageInfo<>(competitionApplies);
        List<CompetitionApply> list1 = pageInfo.getList();
        PageDto<CompetitionApply> pageDto = new PageDto<CompetitionApply>();
        pageDto.setItems(list1);
        pageDto.setTotal((int) pageInfo.getTotal());
        return  R.ok(pageDto);

    }



    
}
