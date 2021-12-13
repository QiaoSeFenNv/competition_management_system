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
@Api("比赛记录接口")
@RequestMapping("/record")
public class RecordController {

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
    UserInfoService userInfoService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;




    @PostMapping("/insertApproval")
    @ApiOperation(value = "插入一条申请信息",notes = "需要传入一个对象（对象封装两个小对象）")
    @Transactional(rollbackFor = {Exception.class})
    public R insertApproval(HttpServletRequest request, @RequestBody CompetitionRecord competitionRecord){

        //从token中拿到账号 拿到具体的对象信息
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectByUserId(username);
        UserInfo userInfo = userInfoService.selectByWorkId(user.getUserId());


        Snowflake snowflake = IdUtil.getSnowflake();
        long approvalId = snowflake.nextId();
        /*
        * 这里开始不同
        * */
        String[] recordWinningStudents = competitionRecord.getRecordWinningStudents();

        String winStudent = "";
        for (String recordWinningStudent : recordWinningStudents) {
            winStudent += recordWinningStudent+",";
        }

        competitionRecord.setRecordWinningStudent(winStudent);

        //生成long类型的id.插入到对应对象中
        long recordId = snowflake.nextId();

        //record表写进数据库
        competitionRecord.setRecordId(recordId);

        //拼接多文件路径
        String recordUpload = "";
        String[] recordUploads = competitionRecord.getRecordUploads();
        for (String upload : recordUploads) {
            recordUpload +=upload+",";
        }
        competitionRecord.setRecordUpload(recordUpload);
        /*
        * 不同结束
        * */

        //生成一个对应内容的申请表
        CompetitionApproval competitionApproval =
                competitionApprovalService.SendApproval(
                        approvalId,recordId,userInfo,1L
                );

        //插入对应数据库中
        int i = competitionApprovalService.insertSelective(competitionApproval);
        int j = competitionRecordService.insertSelective(competitionRecord);


        //插入完毕需要注意todo表也会立刻生一条相关数据,因此也需要插入到todo中  这调是发起者的
        CompetitionTodo competitionTodo = CompetitionTodo.builder()
                .applicantId(user.getUserId())     //拥有者
                .applicantName(userInfo.getUserName())
                .approvalId(approvalId)     //申请表id
                .todoStatus((byte) 0)       //状态
                .todoType("比赛信息申请")     //写死的类型
                .createTime(DateUtil.date())       //创建时间
                .build();
        //将todo表插入
        int k = competitionTodoService.insertSelective(competitionTodo);

        /*
        * process审核流程发生变化
        * */
        //根据process发送给第一个审判者
        String applicantId = competitionProcessService.passProcess(competitionApproval.getProcessId(),userInfo);

        //插入一条新的记录 拥有者不同的记录  这一条是第一个责任人的
        competitionTodo.setApplicantId(applicantId);
        int q = competitionTodoService.insertSelective(competitionTodo);

        //之后需要更新approval取更新流程编号
//        CompetitionProcess competitionProcess = competitionProcessService.selectByPrimaryKey(competitionApproval.getProcessId());
//        competitionApproval.setProcessId(competitionProcess.getNextId());
//        int p = competitionApprovalService.updateByPrimaryKeySelective(competitionApproval);

        return R.ok("");
    }





}
