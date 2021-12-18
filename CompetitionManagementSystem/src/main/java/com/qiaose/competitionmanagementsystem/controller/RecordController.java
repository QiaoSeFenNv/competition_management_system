package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.components.SchedulerMail;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.SysApproval;
import com.qiaose.competitionmanagementsystem.service.*;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import com.qiaose.competitionmanagementsystem.service.serviceImpl.CompetitionProgramServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Autowired
    SchedulerMail schedulerMail;



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

        List<SysRoleUserTable> sysRoleUserTable = sysRoleUserTableService.selectByUserId(user.getUserId());
        List<SysRoleTable> sysRoleTables = new ArrayList<>();
        for (SysRoleUserTable roleUserTable : sysRoleUserTable) {
            List<SysRoleTable> sysRoleTables1 = sysRoleTableService.selectByPrimaryKey(roleUserTable.getRoleId());
            for (SysRoleTable sysRoleTable : sysRoleTables1) {
                sysRoleTables.add(sysRoleTable);
            }
        }
        Boolean flag = false;
        for (SysRoleTable sysRoleTable : sysRoleTables) {
            System.out.println(sysRoleTable);
            if ("ROLE_STUDENT".equals(sysRoleTable.getRoleName() )||
                    "学生用户".equals(sysRoleTable.getDescription())  ) {
                flag = true;
                break;
            }
        }
        System.out.println(flag);
        if (!flag){
            throw new RuntimeException("只有学生可以申请比赛记录");
        }


        Snowflake snowflake = IdUtil.getSnowflake();
        long recordId = snowflake.nextId();
        long approvalId = snowflake.nextId();
        /*
        * 这里开始不同
        * */
        //生成long类型的id.插入到对应对象中

        CompetitionRecord Record = changeInsert(competitionRecord);
        //record表写进数据库
        Record.setRecordId(recordId);
        //写进当前学生
        Record.setRecordWinningStudent(user.getUserId());
        //生成一个对应内容的申请表
        CompetitionApproval competitionApproval =
                competitionApprovalService.SendApproval(
                        approvalId,recordId,userInfo,1L
                );

        //插入对应数据库中
        int i = competitionApprovalService.insertSelective(competitionApproval);
        int j = competitionRecordService.insertSelective(Record);


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
        //发送邮件到辅导员,通知他操作申请
//        String mailText = "【竞赛管理系统】 申请通知:  "+userInfo.getUserName()+
//                "  发送的比赛记录申请请求操作" +
//                ",请登录【竞赛管理系统】今早进行操作,谢谢！";
//        UserInfo fdu = userInfoService.selectByWorkId(applicantId);
//        schedulerMail.toMail(fdu.getEmail(),mailText);

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





    @GetMapping("/getAllRecord")
    @ApiOperation(value = "查询比赛申请记录",notes = "请求头")
    @Transactional(rollbackFor = {Exception.class})
    public R getAllRecord(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        System.out.println(token);
        //
        String userId = jwtTokenUtil.getUsernameFromToken(token);

        List<SysRoleUserTable> sysRoleUserTables = sysRoleUserTableService.selectByUserId(userId);
        List<SysRoleTable> sysRoleTables = new ArrayList<>();
        for (SysRoleUserTable roleUserTable : sysRoleUserTables) {
            List<SysRoleTable> sysRoleTables1 = sysRoleTableService.selectByPrimaryKey(roleUserTable.getRoleId());
            for (SysRoleTable sysRoleTable : sysRoleTables1) {
                sysRoleTables.add(sysRoleTable);
            }
        }

        Integer flag = 0;

        for (SysRoleTable sysRoleTable : sysRoleTables) {
            if (sysRoleTable.getRoleName().equals("ROLE_ADMIN")) {
                flag = 1;
                break;
            }else if (sysRoleTable.getRoleName().equals("ROLE_FDY")){
                flag = 2;
                break;
            }else if(sysRoleTable.getRoleName().equals("ROLE_STUDENT")){
                flag = 3;
                break;
            }
        }


        //共有属性
        List<CompetitionRecord> competitionRecords = new ArrayList<>();

        //管理员 看全部
        if (flag ==1){
            List<CompetitionRecord> competitionRecords1 = competitionRecordService.selectAll();
            for (CompetitionRecord competitionRecord : competitionRecords1) {
                competitionRecords.add( changeShow(competitionRecord));
            }
        }else if (flag ==2){
            //老师看自己的学生
            List<UserInfo> userInfos = new ArrayList<>();
           List<CollegeInfo> collegeInfoList = collegeInfoService.selectDutyId(userId);
            for (CollegeInfo collegeInfo : collegeInfoList) {
                Integer id = collegeInfo.getId();
                 userInfos.addAll(userInfoService.selectByDeptId(id + ""));
            }
            //查出学生 将学生对应的申请表取除，进行查询
            for (UserInfo userInfo : userInfos) {
                List<CompetitionApproval> competitionApprovals = competitionApprovalService.selectByApplicantId(userInfo.getUserId());
                for (CompetitionApproval competitionApproval : competitionApprovals) {
                    CompetitionRecord change = changeShow(competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid()));
                    competitionRecords.add(  change );
                }
            }

        }else if(flag ==3){
            //看自己的
            List<CompetitionApproval> competitionApprovals = competitionApprovalService.selectByApplicantId(userId);
            for (CompetitionApproval competitionApproval : competitionApprovals) {
                CompetitionRecord change = changeShow(competitionRecordService.selectByPrimaryKey(competitionApproval.getApplicantContentid()));
                competitionRecords.add(  change );
            }

        }

        return  R.ok(competitionRecords);
    }



    @PostMapping("/updateRecord")
    @ApiOperation(value = "更新数据",notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R updateRecord(@RequestBody CompetitionRecord competitionRecord){

        CompetitionRecord competitionRecord1 = changeInsert(competitionRecord);
        int i = competitionRecordService.updateByPrimaryKeySelective(competitionRecord1);

        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }


    @PostMapping("/insertRecord")
    @ApiOperation(value = "插入数据",notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R insertRecord(@RequestBody CompetitionRecord competitionRecord){
        CompetitionRecord competitionRecord1 = changeInsert(competitionRecord);

        Snowflake snowflake = IdUtil.getSnowflake();
        long recordId = snowflake.nextId();

        competitionRecord1.setRecordId(recordId);

        int i = competitionRecordService.insertSelective(competitionRecord1);

        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }


    @PostMapping("/deleteRecord")
    @ApiOperation(value = "删除",notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteRecord(@RequestBody CompetitionRecord competitionRecord){

        int i = competitionRecordService.deleteByPrimaryKey(competitionRecord.getRecordId());

        if (i<=0) {
            return R.failed("");
        }
        return R.ok("");
    }




    public CompetitionRecord changeShow(CompetitionRecord competitionRecord){
        //1827302
        String recordWinningStudent = competitionRecord.getRecordWinningStudent();

        competitionRecord.setRecordWinningStudent(
                userInfoService.selectByWorkId(recordWinningStudent).getUserName());

        //返回数组类型的upload
        if (competitionRecord.getRecordUpload()!=null){
            String[] recordUploads = competitionRecord.getRecordUpload().split(",");
            competitionRecord.setRecordUploads(recordUploads);
        }
        return competitionRecord;
    }

    public CompetitionRecord changeInsert(CompetitionRecord competitionRecord){

        //拼接多文件路径
        String recordUpload = "";
        String[] recordUploads = competitionRecord.getRecordUploads();
        for (String upload : recordUploads) {
            recordUpload +=upload+",";
        }
        competitionRecord.setRecordUpload(recordUpload);

        return competitionRecord;
    }


}
