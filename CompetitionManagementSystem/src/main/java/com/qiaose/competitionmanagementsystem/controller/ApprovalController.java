package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.entity.dto.SysApproval;
import com.qiaose.competitionmanagementsystem.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api("学生申请表")
@RequestMapping("/record")
public class ApprovalController {

    //三个接口一同实现一个功能
    @Autowired
    CompetitionApprovalService competitionApprovalService;

    @Autowired
    CompetitionRecordService competitionRecordService;

    @Resource
    CompetitionTodoService competitionTodoService;

    @Resource
    CompetitionProcessService competitionProcessService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @PostMapping("/insertApproval")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R insertApproval(HttpServletRequest request, @RequestBody SysApproval sysApproval){
        //从token中拿到账号
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);

        //接收之后需要将对象分解，分别插入对应的表中
        CompetitionApproval competitionApproval = sysApproval.getCompetitionApproval();
        CompetitionRecord competitionRecord = sysApproval.getCompetitionRecord();


        //生成long类型的id.插入到对应对象中
        Snowflake snowflake = IdUtil.getSnowflake();
        long approvalId = snowflake.nextId();
        long recordId = snowflake.nextId();
        competitionApproval.setApprovalId(approvalId);
        competitionRecord.setRecordId(recordId);

        //插入对应数据库中
        int i = competitionApprovalService.insertSelective(competitionApproval);
        int j = competitionRecordService.insertSelective(competitionRecord);

        //插入完毕需要注意todo表也会立刻生一条相关数据,因此也需要插入到todo中
        CompetitionTodo competitionTodo = CompetitionTodo.builder()
                .applicantId(user.getAccountName())
                .approvalId(approvalId)
                .todoStatus((byte) 0)
                .todoType("比赛信息申请")
                .createTime(DateUtil.date())
                .build();
        //将todo表插入
        int k = competitionTodoService.insertSelective(competitionTodo);

        //根据process发送给第一个审判者
        CompetitionProcess competitionProcess = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());

        competitionTodo.setApplicantId(competitionProcess.getApproverId());

        int q = competitionTodoService.insertSelective(competitionTodo);

        //之后需要更新approval取更新流程编号
        competitionApproval.setProcessId(competitionProcess.getNextId());
        int p = competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);

        return R.ok("");
    }



    @GetMapping("/getCurApproval")
    @ApiOperation(value = "获取申请表与记录标的信息",notes = "需要传入todo中的申请表id即可")
    @Transactional(rollbackFor = {Exception.class})
    public R getCurApproval(@RequestBody SysApproval sysApproval){
        Long todoId = sysApproval.getTodoId();
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(todoId);

        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());

        sysApproval.setCompetitionApproval(competitionApproval);

        CompetitionRecord competitionRecord = competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid());

        sysApproval.setCompetitionRecord(competitionRecord);

        return R.ok(sysApproval);

    }

}
