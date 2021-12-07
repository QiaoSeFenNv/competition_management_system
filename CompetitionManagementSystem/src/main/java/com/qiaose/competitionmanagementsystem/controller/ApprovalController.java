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
import java.util.List;

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

    @Resource
    StudentInfoService studentInfoService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;


    @PostMapping("/insertApproval")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R insertApproval(HttpServletRequest request, @RequestBody SysApproval sysApproval){
        //从token中拿到账号 拿到具体的对象信息
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);
        StudentInfo studentInfo = studentInfoService.selectByStuId(user.getAccountName());

        //注意前端可能不会传递这个对象，所以自己生成一个
        CompetitionApproval competitionApproval = new CompetitionApproval();
//        competitionApproval = sysApproval.getCompetitionApproval();
        //接收之后需要将对象分解，分别插入对应的表中
        CompetitionRecord competitionRecord = sysApproval.getCompetitionRecord();
        //将前端传来的数组变为字符串
        String[] recordWinningStudents = competitionRecord.getRecordWinningStudents();
        String winStudent = "";
        for (String recordWinningStudent : recordWinningStudents) {
            winStudent += recordWinningStudent;
        }
        competitionRecord.setRecordWinningStudent(winStudent);
        //生成long类型的id.插入到对应对象中
        Snowflake snowflake = IdUtil.getSnowflake();
        long approvalId = snowflake.nextId();
        long recordId = snowflake.nextId();
        competitionApproval.setApprovalId(approvalId);
        competitionRecord.setRecordId(recordId);

        //有些对象前端无法填写
        competitionApproval.setApplicantContentid(recordId);
        competitionApproval.setApplicantId(studentInfo.getStuId());
        competitionApproval.setApplicantName(studentInfo.getName());
        competitionApproval.setApprovalStatus((byte)0);

        //插入对应数据库中
        int i = competitionApprovalService.insertSelective(competitionApproval);
        int j = competitionRecordService.insertSelective(competitionRecord);


        //插入完毕需要注意todo表也会立刻生一条相关数据,因此也需要插入到todo中
        CompetitionTodo competitionTodo = CompetitionTodo.builder()
                .applicantId(user.getAccountName())     //拥有者
                .approvalId(approvalId)     //申请表id
                .todoStatus((byte) 0)       //状态
                .todoType("比赛信息申请")     //写死的类型
                .createTime(DateUtil.date())       //创建时间
                .build();
        //将todo表插入
        int k = competitionTodoService.insertSelective(competitionTodo);

        //根据process发送给第一个审判者
        CompetitionProcess competitionProcess = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());
        //修改之前事务表的拥有者
        competitionTodo.setApplicantId(competitionProcess.getApproverId());
        //插入一条新的记录 拥有者不同的记录
        int q = competitionTodoService.insertSelective(competitionTodo);

        //之后需要更新approval取更新流程编号
        competitionApproval.setProcessId(competitionProcess.getNextId());
        int p = competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);

        return R.ok("");
    }



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
        //根据申请表的id 查询记录表
        CompetitionRecord competitionRecord = competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid());
        //将记录表内容注入到sysApproval对象
        sysApproval.setCompetitionRecord(competitionRecord);
        //返回给前端
        return R.ok(sysApproval);

    }


    @PostMapping("/agreeTodo")
    @ApiOperation(value = "老师同意",notes = "上传一个对象原封不懂的上传")
    @Transactional(rollbackFor = {Exception.class})
    public R agreeTodo(@RequestBody SysApproval sysApproval){
        //获得当前的申请流程
        CompetitionApproval competitionApproval = sysApproval.getCompetitionApproval();
        //获得最新的申请流程
        CompetitionProcess competitionProcess = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());


        //如果下一个编号为空则结束
        if (competitionProcess.getNextId() == null){
            //修改申请表的状态为同意 1为同意 0执行中 2拒绝
            competitionApproval.setApprovalStatus((byte)1);
            //更新申请表的状态
            competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
            //连带事务表的中关于这个申请的状态也发生改变  根据申请表id来
            List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
            for (CompetitionTodo competitionTodo : competitionTodos) {
                //修改每个事项表的状态为 同意
                competitionTodo.setTodoStatus(competitionApproval.getApprovalStatus());
                competitionTodoService.updateByPrimaryKeySelective(competitionTodo);
            }
            return R.ok("");
        }

        //先获得当前的todo对象
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(sysApproval.getTodoId());


        //如果下一个编号不为空
        //更新表的下一个审批者
        competitionApproval.setProcessId(competitionProcess.getNextId());
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
        //插入一条todo表
        competitionTodo.setApplicantId(competitionProcess.getApproverId());
        competitionTodoService.insertSelective(competitionTodo);

        return R.ok("");
    }



    @GetMapping("/rejectTodo")
    @ApiOperation(value = "老师拒绝",notes = "对象里面只要修改rejectReason")
    @Transactional(rollbackFor = {Exception.class})
    public R rejectTodo(@RequestBody SysApproval sysApproval){
        //从中获得更新的拒绝的内容,准备进行更新
        CompetitionApproval competitionApproval = sysApproval.getCompetitionApproval();
        //修改状态为驳回
        competitionApproval.setApprovalStatus((byte)2);
        //填写拒绝原因
        competitionApproval.setRejectReson(sysApproval.getCompetitionApproval().getRejectReson());
        //更新申请表内容
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
        //修改每个事务表的状态
        List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
        for (CompetitionTodo competitionTodo : competitionTodos) {
            competitionTodo.setTodoStatus(competitionApproval.getApprovalStatus());
            competitionTodoService.updateByPrimaryKeySelective(competitionTodo);
        }

        return R.ok("");
    }

}
