package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.components.SchedulerMail;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.entity.dto.SysApproval;
import com.qiaose.competitionmanagementsystem.service.*;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api("用户申请表")
@RequestMapping("/approval")
public class ApprovalController {

    //三个接口一同实现一个功能
    @Autowired
    CompetitionApprovalService competitionApprovalService;

    @Autowired
    CompetitionRecordService competitionRecordService;

    @Autowired
    CompetitionProgramService competitionProgramService;

    @Resource
    CompetitionTodoService competitionTodoService;

    @Resource
    CompetitionProcessService competitionProcessService;

    @Autowired
    CompetitionOrganizerService competitionOrganizerService;

    @Autowired
    CollegeInfoService collegeInfoService;

    @Autowired
    CompetitionRewardService competitionRewardService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    SchedulerMail schedulerMail;


    @GetMapping("/getCurApproval")
    @ApiOperation(value = "获取当前用户申请表与记录表的信息",notes = "需要传入todo事务表的id即可")
    public R getCurApproval(@RequestParam Long todoId){
        //创建一个新的申请对象,到时候用于返回前端
        SysApproval sysApproval = new SysApproval();
        //根据todoId查询 事务信息
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(todoId);
        if (competitionTodo == null) {
            return R.failed("无事项内容");
        }
        //根据查询出来的事务信息 查询申请表
        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());
        //将申请表内容注入到sysApproval对象
        sysApproval.setCompetitionApproval(competitionApproval);

        String todoType = competitionTodo.getTodoType();

        //怎么判断是那个表勒
        if ( "比赛记录申请".equals(todoType) ){
            System.out.println(competitionTodo);
            //根据申请表的id 查询记录表
            CompetitionRecord competitionRecord = competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid());
            //将数据库中的字符串变为数组
            String recordWinningStudent = competitionRecord.getRecordWinningStudent();
            //用数组接收
            competitionRecord.setRecordWinningStudent(
                    userInfoService.selectByWorkId(recordWinningStudent).getUserName());
            //将数组放入返回体中
            //返回数组类型的upload
            if (competitionRecord.getRecordUpload()!=null){
                String[] recordUploads = competitionRecord.getRecordUpload().split(",");
                competitionRecord.setRecordUploads(recordUploads);
            }
            competitionRecord.setRecordCompetitionName( competitionOrganizerService.selectByPrimaryKey(
                    competitionRecord.getRecordCompetitionId() ).getOrganizeOrganizer());

            competitionRecord.setRecordRewardName(competitionRewardService.selectByPrimaryKey(
                    competitionRecord.getRecordRewardId() ).getRewardLevel());
            competitionRecord.setRecordCollegeName(collegeInfoService.selectByPrimaryKey(
                    competitionRecord.getRecordCollegeId() ).getCollegeName());
            //将记录表内容注入到sysApproval对象
            sysApproval.setContent(competitionRecord);
        }


        List<CompetitionProgram> competitionPrograms = competitionProgramService.selectByApproval(competitionTodo.getApprovalId());

        sysApproval.setCompetitionProgram(competitionPrograms);
        //返回给前端
        return R.ok(sysApproval);

    }


    @PostMapping("/agreeTodo")
    @ApiOperation(value = "老师同意",notes = "上传一个对象,里面放置两个内容下一个流程的id")
    @Transactional(rollbackFor = {Exception.class})
    public R agreeTodo(@RequestBody SysApproval sysApproval){

        Long todoId = sysApproval.getTodoId();
        //获得当前的事务对象
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(todoId);

        //获得当前的申请内容
        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());
        //获得当前流程
        CompetitionProcess competitionProcessOld = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());


        //如果下一个编号为空则结束
        if (competitionProcessOld.getNextId() == null && competitionTodo.getTodoStatus() == (byte)0){
            //修改申请表的状态为同意 0执行中 1为同意  2拒绝
            competitionApproval.setApprovalStatus((byte)1);
            //更新申请表的状态
            competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
            //连带事务表的中关于这个申请的状态也发生改变  根据申请表id来

            //根据事务表上得所有者进行更新                                                                    //userId
            CompetitionProgram competitionProgram = competitionProgramService.selectUserIdAndApproval(competitionTodo.getApplicantId(),
                    //申请id
                    competitionApproval.getApprovalId());
            if(competitionProgram.getState() == (byte)2 || competitionProgram.getState() == (byte)1){
                throw new RuntimeException("状态已同意或结束");
            }
            competitionProgram.setState((byte)1);
            competitionProgram.setComplete(DateKit.getNow());
            competitionProgramService.updateByPrimaryKeySelective(competitionProgram);

            List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
            for (CompetitionTodo Todo : competitionTodos) {
                //修改每个事项表的状态为 同意
                Todo.setTodoStatus(competitionApproval.getApprovalStatus());
                competitionTodoService.updateByPrimaryKeySelective(Todo);
            }
            //获得学生的学号
            UserInfo userInfo = userInfoService.selectByWorkId(competitionApproval.getApplicantId());
            String mailText = "【竞赛管理系统】 申请通知:  "+userInfo.getUserName()+
                    "  发送的比赛记录申请请求操作" +
                    ",同意该学生的申请！";
            schedulerMail.toMail(userInfo.getEmail(),mailText);
            return R.ok("");
        }

        //根据事务表上得所有者进行更新 0---执行中 1--同意 2---拒绝 3--未开始
        CompetitionProgram competitionProgram = competitionProgramService.selectUserIdAndApproval(competitionTodo.getApplicantId(),
                competitionTodo.getApprovalId());

        if(competitionProgram.getState() == (byte)2 || competitionProgram.getState() == (byte)1){
            throw new RuntimeException("状态已同意或结束");
        }

        System.out.println(competitionProgram);
        competitionProgram.setState((byte)1);
        competitionProgramService.updateByPrimaryKeySelective(competitionProgram);

        UserInfo userInfo = userInfoService.selectByWorkId(competitionApproval.getApplicantId());
        //获得更新之后的责任人的id

        String applicantId = null;
        try {
            applicantId = competitionProcessService.passProcess(competitionProcessOld.getNextId(),userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //更新表的下一个审批者
        competitionApproval.setProcessId(competitionProcessOld.getNextId());
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);

        //插入一条todo表
        competitionTodo.setApplicantId(applicantId);
        competitionTodoService.insertSelective(competitionTodo);


        String mailText = "【竞赛管理系统】 申请通知:  "+userInfo.getUserName()+
                "  发送的比赛记录申请请求操作" +
                ",请登录【竞赛管理系统】今早进行操作,谢谢！";
        UserInfo teacher = userInfoService.selectByWorkId(applicantId);
        schedulerMail.toMail(teacher.getEmail(),mailText);

        return R.ok("");
    }



    @PostMapping("/rejectTodo")
    @ApiOperation(value = "老师拒绝",notes = "对象里面只要修改rejectReason")
    @Transactional(rollbackFor = {Exception.class})
    public R rejectTodo(@RequestBody SysApproval sysApproval){

        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(sysApproval.getTodoId());

        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());

        CompetitionProgram competitionProgram = competitionProgramService.selectUserIdAndApproval(competitionTodo.getApplicantId(),
                competitionTodo.getApprovalId());

        if(competitionProgram.getState() == (byte)2 || competitionProgram.getState() == (byte)1){
            throw new RuntimeException("状态已同意或结束");
        }
        competitionProgram.setState((byte)2);
        competitionProgram.setComplete(DateKit.getNow());
        competitionProgramService.updateByPrimaryKeySelective(competitionProgram);

        //修改状态为驳回
        competitionApproval.setApprovalStatus((byte)2);
        //填写拒绝原因
        competitionApproval.setRejectReson(sysApproval.getCompetitionApproval().getRejectReson());
        //更新申请表内容
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
        //修改每个事务表的状态
        List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
        for (CompetitionTodo Todo : competitionTodos) {
            Todo.setTodoStatus(competitionApproval.getApprovalStatus());
            competitionTodoService.updateByPrimaryKeySelective(Todo);
        }

        UserInfo userInfo = userInfoService.selectByWorkId(competitionApproval.getApplicantId());
        String mailText = "【竞赛管理系统】 申请通知:  "+userInfo.getUserName()+
                "  发送的比赛记录申请请求操作" +
                ",拒绝该学生的申请！理由如下"+"【"+competitionApproval.getRejectReson()+"】";
        schedulerMail.toMail(userInfo.getEmail(),mailText);

        return R.ok("");
    }

}
