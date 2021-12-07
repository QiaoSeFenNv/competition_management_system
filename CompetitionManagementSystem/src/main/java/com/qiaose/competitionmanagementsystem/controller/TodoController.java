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

    @GetMapping("/getCurTodo")
    @ApiOperation(value = "获得当前用户的todo信息",notes = "直接传递一个请求头,携带token")
    public R getStuCurTodo(HttpServletRequest request){
        //从token中拿到账号
        String token = request.getHeader("Authorization");

        if (token == null){
            return R.failed("用户信息错误,请重新登录");
        }

        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByAccountName(username);

        if (user.getAccountName() == null){
            return R.failed("用户信息错误,请重新登录");
        }

        List<CompetitionTodo> competitionTodo = competitionTodoService.selectByApplicantId(user.getAccountName());

        return R.ok(competitionTodo);
    }
}
