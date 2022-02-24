package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.components.SchedulerMail;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.entity.dto.RecordDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysApproval;
import com.qiaose.competitionmanagementsystem.enums.RecordTypeEnum;
import com.qiaose.competitionmanagementsystem.enums.TodoStateEnum;
import com.qiaose.competitionmanagementsystem.service.*;
import com.qiaose.competitionmanagementsystem.utils.DateKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Resource
    CompetitionBonusService competitionBonusService;

    @Resource
    ICompetitionAwardService iCompetitionAwardService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    SchedulerMail schedulerMail;

    @GetMapping("/getCurApproval")
    @ApiOperation(value = "获取当前用户申请表与记录表的信息", notes = "需要传入todo事务表的id即可")
    public R getCurApproval(@RequestParam Long todoId) {
        //创建一个新的申请对象,到时候用于返回前端
        SysApproval sysApproval = new SysApproval();
        //根据todoId查询 事务信息
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(todoId);
        if (competitionTodo == null) {
            return R.failed("无事项内容");
        }
        sysApproval.setTodoState(competitionTodo.getTodoStatus());
        //根据查询出来的事务信息 查询申请表
        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());
        //将申请表内容注入到sysApproval对象
        sysApproval.setCompetitionApproval(competitionApproval);

        String todoType = competitionTodo.getTodoType();

        //怎么判断是那个表勒
        if ("比赛记录申请".equals(todoType)) {
            System.out.println(competitionTodo);
            //根据申请表的id 查询记录表
            CompetitionRecord competitionRecord = competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid());
            //将数据库中的字符串变为数组
            String recordWinningStudent = competitionRecord.getRecordWinningStudent();
            //用数组接收
            competitionRecord.setRecordWinningStudent(
                    userInfoService.selectByWorkId(recordWinningStudent).getUserId());
            //将数组放入返回体中
            //返回数组类型的upload
            if (competitionRecord.getRecordUpload() != null) {
                String[] recordUploads = competitionRecord.getRecordUpload().split(",");
                competitionRecord.setRecordUploads(recordUploads);
            }
            competitionRecord.setRecordCompetitionName(competitionOrganizerService.selectByPrimaryKey(
                    competitionRecord.getRecordCompetitionId()).getOrganizeOrganizer());

            competitionRecord.setRecordRewardName(competitionRewardService.selectByPrimaryKey(
                    competitionRecord.getRecordRewardId()).getRewardLevel());
            competitionRecord.setRecordCollegeName(collegeInfoService.selectByPrimaryKey(
                    competitionRecord.getRecordCollegeId()).getCollegeName());
            //将记录表内容注入到sysApproval对象
            sysApproval.setContent(competitionRecord);
        }

        if ("奖金申请".equals(todoType)) {
            CompetitionBonus competitionBonus = competitionBonusService.selectByPrimaryKey(competitionApproval.getApplicantContentid());
            sysApproval.setContent(competitionBonus);
        }

        List<CompetitionProgram> competitionPrograms = competitionProgramService.selectByApproval(competitionTodo.getApprovalId());

        sysApproval.setCompetitionProgram(competitionPrograms);
        //返回给前端
        return R.ok(sysApproval);

    }

    @PostMapping("/agreeTodo")
    @ApiOperation(value = "老师同意", notes = "上传一个对象,里面放置两个内容下一个流程的id")
    @Transactional(rollbackFor = {Exception.class})
    public R agreeTodo(@RequestBody SysApproval sysApproval) {

        Long todoId = sysApproval.getTodoId();
        //获得当前的事务对象
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(todoId);

        //获得当前的申请内容
        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());
        //获得当前流程
        CompetitionProcess competitionProcessOld = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());

        //如果下一个编号为空则结束
        if (competitionProcessOld.getNextId() == null && Objects.equals(competitionTodo.getTodoStatus(), TodoStateEnum.IN_PROGRESS.getCode())) {
            //修改申请表的状态为同意 0执行中 1为同意  2拒绝
            competitionApproval.setApprovalStatus(TodoStateEnum.AGREE.getCode());
            //更新申请表的状态
            competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
            //连带事务表的中关于这个申请的状态也发生改变  根据申请表id来

            //根据事务表上得所有者进行更新                                                                    //userId
            CompetitionProgram competitionProgram = competitionProgramService.selectUserIdAndApproval(competitionTodo.getApplicantId(),
                    //申请id
                    competitionApproval.getApprovalId());
            if (competitionProgram.getState().equals(TodoStateEnum.FINISH.getCode())) {
                return R.failed("状态已同意或结束");
            }
            competitionProgram.setState(TodoStateEnum.FINISH.getCode());
            competitionProgram.setComplete(DateKit.getNow());
            competitionProgramService.updateByPrimaryKeySelective(competitionProgram);

            List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
            for (CompetitionTodo Todo : competitionTodos) {
                //修改每个事项表的状态为 同意
                Todo.setTodoStatus(competitionApproval.getApprovalStatus());
                competitionTodoService.updateByPrimaryKeySelective(Todo);
            }
            //生成一条验证记录
            insertCompetitionAward(competitionApproval);

            return R.ok("");
        }

        //根据用户和申请id获取
        CompetitionProgram competitionProgram = competitionProgramService.selectUserIdAndApproval(competitionTodo.getApplicantId(),
                competitionTodo.getApprovalId());

        //如果是流程进度状态是完成 状态再次点击就不可执行
        if (competitionProgram.getState().equals(TodoStateEnum.FINISH.getCode())) {
            return R.failed("重复执行");
        }

        competitionProgram.setState(TodoStateEnum.FINISH.getCode());
        //可选是否填写意见信息放置todo对象中
        if (!StringUtils.isEmpty(sysApproval.getAdvice())) {
            competitionProgram.setAdvice(sysApproval.getAdvice());
        }
        competitionProgramService.updateByPrimaryKeySelective(competitionProgram);

        UserInfo userInfo = userInfoService.selectByWorkId(competitionApproval.getApplicantId());
        //获得更新之后的责任人的id

        String applicantId = null;
        try {
            applicantId = competitionProcessService.passProcess(competitionProcessOld.getNextId(), userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //更新状态为同意
        competitionTodo.setTodoStatus(TodoStateEnum.AGREE.getCode());
        competitionTodoService.updateByPrimaryKeySelective(competitionTodo);

        //更新表的下一个审批者
        competitionApproval.setProcessId(competitionProcessOld.getNextId());
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);

        //插入下一条todo表
        competitionTodo.setApplicantId(applicantId);
        //设置下一个todo表的状态 改为进行中
        competitionTodo.setTodoStatus(TodoStateEnum.IN_PROGRESS.getCode());
        competitionTodoService.insertSelective(competitionTodo);

        //更新下一个节点为进行中
        CompetitionProgram program = competitionProgramService.selectUserIdAndApproval(applicantId,
                competitionTodo.getApprovalId());
        program.setState(TodoStateEnum.IN_PROGRESS.getCode());
        competitionProgramService.updateByPrimaryKeySelective(program);

        return R.ok("");
    }

    @PostMapping("/rejectTodo")
    @ApiOperation(value = "老师拒绝", notes = "对象里面只要修改rejectReason")
    @Transactional(rollbackFor = {Exception.class})
    public R rejectTodo(@RequestBody SysApproval sysApproval) {

        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(sysApproval.getTodoId());

        CompetitionApproval competitionApproval = competitionApprovalService.selectByPrimaryKey(competitionTodo.getApprovalId());

        CompetitionProgram competitionProgram = competitionProgramService.selectUserIdAndApproval(competitionTodo.getApplicantId(),
                competitionTodo.getApprovalId());

        if (competitionProgram.getState().equals(TodoStateEnum.DISAGREE.getCode())) {
            return R.failed("重复执行");
        }
        //可选是否填写意见信息放置todo对象中
        if (!StringUtils.isEmpty(sysApproval.getAdvice())) {
            competitionProgram.setAdvice(sysApproval.getAdvice());
        }
        competitionProgram.setState(TodoStateEnum.DISAGREE.getCode());
        competitionProgram.setComplete(DateKit.getNow());
        competitionProgramService.updateByPrimaryKeySelective(competitionProgram);

        //修改状态为驳回
        competitionApproval.setApprovalStatus(TodoStateEnum.DISAGREE.getCode());
        //填写拒绝原因
        competitionApproval.setRejectReson(sysApproval.getAdvice());
        //更新申请表内容
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
        //修改每个事务表的状态
        List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
        for (CompetitionTodo Todo : competitionTodos) {
            Todo.setTodoStatus(competitionApproval.getApprovalStatus());
            competitionTodoService.updateByPrimaryKeySelective(Todo);
        }

        competitionTodo.setTodoStatus(TodoStateEnum.DISAGREE.getCode());
        competitionTodoService.updateByPrimaryKeySelective(competitionTodo);
        return R.ok("");
    }

    public void insertCompetitionAward(CompetitionApproval competitionApproval) {
        //查询对应记录信息
        CompetitionRecord competitionRecord = competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid());
        //生成一条验证过的比赛信息
        CompetitionAward competitionAward = new CompetitionAward();
        competitionAward.setUserId(competitionApproval.getApplicantId());
        competitionAward.setCompId(Long.valueOf(competitionRecord.getRecordCompetitionId()));
        competitionAward.setAwardTime(competitionRecord.getRecordWinningTime());
        competitionAward.setRewardLevel(competitionRecord.getRecordRewardId());
        competitionAward.setRelateApproval(competitionApproval.getApprovalId());
        competitionAward.setRecordType(RecordTypeEnum.VERIFY.getCode());
        iCompetitionAwardService.save(competitionAward);

    }


}
