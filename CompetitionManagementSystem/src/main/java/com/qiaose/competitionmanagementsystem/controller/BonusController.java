package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.service.*;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bonus")
@Api(value = "奖金记录接口")
public class BonusController {


    @Resource
    CompetitionBonusService competitionBonusService;

    @Autowired
    CompetitionApprovalService competitionApprovalService;

    @Resource
    CompetitionTodoService competitionTodoService;

    @Resource
    CompetitionProcessService competitionProcessService;

    @Autowired
    CompetitionProgramService competitionProgramService;

    @Resource
    CollegeInfoService collegeInfoService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    SysRoleUserTableService sysRoleUserTableService;

    @Resource
    SysRoleTableService sysRoleTableService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;



    @PostMapping("/insertApproval")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R insertApproval(HttpServletRequest request, @RequestBody CompetitionBonus competitionBonus){
        //从token中拿到账号 拿到具体的对象信息
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByUserId(username);
        UserInfo userInfo = userInfoService.selectByWorkId(user.getUserId());

        Snowflake snowflake = IdUtil.getSnowflake();
        long bonusId = snowflake.nextId();
        long approvalId = snowflake.nextId();

        //奖金内容未生成
        competitionBonus.setId(bonusId);
        //计算收税
        BigDecimal shouldSend = competitionBonus.getShouldSend();
        competitionBonus.setRealSend(shouldSend);
        int i1 = competitionBonusService.insertSelective(competitionBonus);

        //插入一个申请表
        CompetitionApproval competitionApproval =
                competitionApprovalService.SendApproval(
                        approvalId,bonusId,userInfo,1L
                );
        int i = competitionApprovalService.insertSelective(competitionApproval);

        //插入完毕需要注意todo表也会立刻生一条相关数据,因此也需要插入到todo中  这调是发起者的
        CompetitionTodo competitionTodo = CompetitionTodo.builder()
                .applicantId(user.getUserId())     //拥有者
                .applicantName(userInfo.getUserName())
                .approvalId(approvalId)     //申请表id
                .todoStatus((byte) 0)       //状态
                .todoType("奖金申请")     //写死的类型
                .createTime(DateUtil.date())       //创建时间
                .build();
        //将todo表插入
        int k = competitionTodoService.insertSelective(competitionTodo);


        String applicantId = null;
        try {
            applicantId = competitionProcessService.passProcess(competitionApproval.getProcessId(),userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //插入一条新的记录 拥有者不同的记录  这一条是第一个责任人的
        competitionTodo.setApplicantId(applicantId);
        int q = competitionTodoService.insertSelective(competitionTodo);

        createComProgram(competitionApproval,userInfo);

        return R.ok("");
    }

    public void createComProgram(CompetitionApproval competitionApproval,UserInfo userInfo){

        System.out.println("----------------进来测试了-------------");
        //获得当前得流程  流程一
        CompetitionProcess process = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());
        //将三个流程都创建一个进度
        //userInfo是学生了 这个不能变要一直存在
        UserInfo temp = null;
        String applicantId = null;
        while (process.getNextId() != null){
            try {
                //这个流程都是用学生来找下一个流程 审核人的
                applicantId = competitionProcessService.passProcess(process.getProcessId(),userInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp = userInfoService.selectByWorkId(applicantId);

            System.out.println(userInfo);
            CompetitionProgram competitionProgram = CompetitionProgram.builder()
                    .approvalId(competitionApproval.getApprovalId())
                    .state((byte)3)
                    .stepname(process.getApproverName())
                    .auditor(temp.getUserName())
                    .userId(temp.getUserId())
                    .build();
            competitionProgramService.insertSelective(competitionProgram);
            process = competitionProcessService.selectByPrimaryKey(process.getNextId());
            //更新辅导员为二级学院
        }
        if(process.getNextId() == null){
            try {
                //这个流程都是用学生来找下一个流程 审核人的
                applicantId = competitionProcessService.passProcess(process.getProcessId(),userInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp = userInfoService.selectByWorkId(applicantId);
            CompetitionProgram competitionProgram = CompetitionProgram.builder()
                    .approvalId(competitionApproval.getApprovalId())
                    .state((byte)3)
                    .stepname(process.getApproverName())
                    .auditor(temp.getUserName())
                    .userId(temp.getUserId())
                    .build();
            competitionProgramService.insertSelective(competitionProgram);
        }
    }
}
