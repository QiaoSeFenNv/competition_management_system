package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import com.qiaose.competitionmanagementsystem.entity.CompetitionProcess;
import com.qiaose.competitionmanagementsystem.entity.CompetitionTodo;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.SysApproval;
import com.qiaose.competitionmanagementsystem.service.CompetitionApprovalService;
import com.qiaose.competitionmanagementsystem.service.CompetitionProcessService;
import com.qiaose.competitionmanagementsystem.service.CompetitionTodoService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(value = "事务接口")
@RequestMapping("/todo")
public class TodoController {


    @Autowired
    CompetitionTodoService competitionTodoService;

    @Autowired
    CompetitionProcessService competitionProcessService;

    @Autowired
    CompetitionApprovalService competitionApprovalService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/stuTodo")
    @ApiOperation(value = "获得学生的todo信息",notes = "需要传入一个对象（对象封装两个小对象）")
    public R getStuCurTodo(HttpServletRequest request){
        //从token中拿到账号
        String token = request.getHeader("Authorization");

        if (token == null){
            return R.failed("信息不全");
        }

        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);

        if (user.getAccountName() == null){
            return R.failed("信息错误");
        }

        List<CompetitionTodo> competitionTodo = competitionTodoService.selectByApplicantId(user.getAccountName());

        return R.ok(competitionTodo);
    }

    @GetMapping("/successTodo")
    @ApiOperation(value = "老师同意",notes = "")
    public R successTodo(@RequestBody SysApproval sysApproval, @RequestParam Long todoId){
        CompetitionApproval competitionApproval = sysApproval.getCompetitionApproval();

        CompetitionProcess competitionProcess = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());

        //如果下一个编号为空则结束
        if (competitionProcess.getNextId() == null){
            competitionApproval.setApprovalStatus((byte)1);
            competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
            List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
            for (CompetitionTodo competitionTodo : competitionTodos) {
                competitionTodo.setTodoStatus(competitionApproval.getApprovalStatus());
                competitionTodoService.updateByPrimaryKeySelective(competitionTodo);
            }
            return R.ok("");
        }

        //先获得当前的todo对象
        CompetitionTodo competitionTodo = competitionTodoService.selectByPrimaryKey(todoId);


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
    @ApiOperation(value = "老师拒绝",notes = "")
    public R rejectTodo(@RequestBody SysApproval sysApproval){
        CompetitionApproval competitionApproval = sysApproval.getCompetitionApproval();

        CompetitionProcess competitionProcess = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());

        competitionApproval.setApprovalStatus((byte)2);
        competitionApproval.setRejectReson(sysApproval.getRejectReason());
        competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);
        List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApprovalId(competitionApproval.getApprovalId());
        for (CompetitionTodo competitionTodo : competitionTodos) {
            competitionTodo.setTodoStatus(competitionApproval.getApprovalStatus());
            competitionTodoService.updateByPrimaryKeySelective(competitionTodo);
        }

        return R.ok("");
    }
}
