package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.service.*;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    UserInfoService userInfoService;

    @Resource
    CompetitionCreditsService competitionCreditsService;

    @Resource
    CompetitionApplyService competitionApplyService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;


    @GetMapping("/getBonus")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R getBonus(HttpServletRequest request,Integer infoId,Double Amount){
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByUserId(username);
        UserInfo userInfo = userInfoService.selectByWorkId(user.getUserId());
        
        CompetitionApply competitionApply = competitionApplyService.selectByUserIdAndInfoId(infoId,user.getUserId());
        String[] split = competitionApply.getApplystudent().split(",");
        List<CompetitionBonus> competitionBonusList = new ArrayList<>();
        for (String userId : split) {
            competitionBonusList.add(competitionBonusService.selectByUserIdAndInfoId(infoId,userId));
        }
        if (Amount>=500){
            //收税
            Amount = Amount * 1;
        }
        for (CompetitionBonus competitionBonus : competitionBonusList) {
            //均分
            competitionBonus.setShouldSend(Amount/split.length);
            competitionBonus.setRealSend(Amount/split.length);
        }

        //以下流程部分

        Snowflake snowflake = IdUtil.getSnowflake();
        long approvalId = snowflake.nextId();
        /*
         * 这里开始不同
         * */
        //生成long类型的id.插入到对应对象中
        //record表写进数据库
        //生成一个对应内容的申请表
        Long bonusId = competitionBonusService.selectByUserIdAndInfoId(infoId, userInfo.getUserId()).getId();
        CompetitionApproval competitionApproval =
                competitionApprovalService.SendApproval(
                        approvalId,bonusId,userInfo,1L
                );

        //插入对应数据库中
        int i = competitionApprovalService.insertSelective(competitionApproval);


        //插入完毕需要注意todo表也会立刻生一条相关数据,因此也需要插入到todo中  这调是发起者的
        CompetitionTodo competitionTodo = CompetitionTodo.builder()
                .applicantId(user.getUserId())     //拥有者
                .applicantName(userInfo.getUserName())
                .approvalId(approvalId)     //申请表id
                .todoStatus((byte) 0)       //状态
                .todoType("比赛记录申请")     //写死的类型
                .createTime(DateUtil.date())       //创建时间
                .build();
        //将todo表插入
        int k = competitionTodoService.insertSelective(competitionTodo);
        /*
         * process审核流程发生变化
         * */
        //根据process发送给第一个审判者
        String applicantId = null;
        try {
            applicantId = competitionProcessService.passProcess(competitionApproval.getProcessId(),userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //插入一条新的记录 拥有者不同的记录  这一条是第一个责任人的
        competitionTodo.setApplicantId(applicantId);
        int q = competitionTodoService.insertSelective(competitionTodo);
        //生成进度内容
        createComProgram(competitionApproval,userInfo);
        return R.ok(competitionBonusList);
    }

    

    @PostMapping("/insertBonus")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R insertBonus(HttpServletRequest request, @RequestBody CompetitionBonus competitionBonus){
        //每一个人都可以插入银行号
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByUserId(username);
        competitionBonus.setUserId(user.getUserId());
        int i1 = competitionBonusService.insertSelective(competitionBonus);
        return R.ok("");
    }



    @GetMapping("/getBonus")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R getBonus(@RequestParam Long rewardId,String level){
        CompetitionCredits competitionCredits = competitionCreditsService.selectByNameAndId(rewardId, level);
        return R.ok(competitionCredits.getBonus());
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
