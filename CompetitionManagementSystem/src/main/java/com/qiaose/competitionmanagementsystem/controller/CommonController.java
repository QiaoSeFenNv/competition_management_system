package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.Hutool;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.components.SchedulerMail;
import com.qiaose.competitionmanagementsystem.entity.*;
import com.qiaose.competitionmanagementsystem.entity.dto.AwardCompetitionDto;
import com.qiaose.competitionmanagementsystem.entity.dto.CommonDto;
import com.qiaose.competitionmanagementsystem.entity.dto.CommonDto2;
import com.qiaose.competitionmanagementsystem.entity.dto.CreditDistributionDto;
import com.qiaose.competitionmanagementsystem.enums.TodoStateEnum;
import com.qiaose.competitionmanagementsystem.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.qiaose.competitionmanagementsystem.utils.DateKit.dateFormat;

@RestController
@Api(value = "公共接口")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource
    CompetitionTodoService competitionTodoService;

    @Resource
    CompetitionRecordService competitionRecordService;

    @Resource
    CompetitionOrganizerService competitionOrganizerService;

    @Resource
    CompetitionApprovalService competitionApprovalService;

    @Resource
    UserService userService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    CompetitionProgramService competitionProgramService;

    @Autowired
    SchedulerMail schedulerMail;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @GetMapping("/sendCode")
    @ApiOperation(value = "发送验证码", notes = "需要输入邮箱地址")
    public R sendCode(@RequestParam @Email String email) {

        RandomGenerator randomGenerator = new RandomGenerator(6);
        //验证码6位
        String generate = randomGenerator.generate();

        //设置邮件内容
        String mailText = "【竞赛管理系统】 验证码：" + generate + "  请勿将验证码告诉他人哦。";

        try {
            String s = stringRedisTemplate.opsForValue().get(email);
            if (s != null) {
                return R.failed("邮箱时间还未过90秒");
            }
            if (!email.isEmpty()) {
                schedulerMail.toMail(email, mailText);
                stringRedisTemplate.opsForValue().set(email, generate, 120, TimeUnit.SECONDS);
                return R.ok("");
            }
        } catch (Exception exception) {
            throw exception;
        }

        return R.failed("发送成功");

    }

    @GetMapping("/templateData")
    @ApiOperation(value = "获取前端模板", notes = "需要输入邮箱地址")
    public R sendCode(HttpServletRequest request) {
        CommonDto commonDto = new CommonDto();
        String token = request.getHeader("Authorization");
        String userId = jwtTokenUtil.getUsernameFromToken(token);

        UserInfo userInfo = userInfoService.selectByWorkId(userId);
        //设置账号获得学分
        commonDto.setIntegral(userInfo.getCreditsEarned());

        List<CompetitionTodo> competitionTodos = competitionTodoService.selectByApplicantId(userId);
        //拿到今天接收的申请
        Date toDay = new Date();
        int count = 0;
        for (CompetitionTodo competitionTodo : competitionTodos) {
            //表中时间和今天比较
            System.out.println();
            String format = DateUtil.format(competitionTodo.getCreateTime(), "yyyy-MM-dd");
            System.out.println(toDay);
            String format1 = DateUtil.format(toDay, "yyyy-MM-dd");
            if (format.equals(format1)) {
                count++;
            }
        }
        commonDto.setToDayReceive(count);
        System.out.println(commonDto.getToDayReceive());
        //拿到今天处理的申请
        count = 0;
        List<CompetitionProgram> competitionProgram = competitionProgramService.selectByUserId(userId);
        for (CompetitionProgram program : competitionProgram) {
            if (program.getComplete() != null){
                String format = DateUtil.format(program.getComplete(), "yyyy-MM-dd");
                String format1 = DateUtil.format(toDay, "yyyy-MM-dd");
                if (format.equals(format1)) {
                    count++;
                }
            }
        }
        commonDto.setToDayDeal(count);
        System.out.println(commonDto.getToDayDeal());
        //学分
        List<CompetitionRecord> sumPrice = competitionRecordService.selectByUserId(userId);
        commonDto.setSumPrice(sumPrice.size());

        return R.ok(commonDto);
    }


    @GetMapping("/analysisData")
    @ApiOperation(value = "获取分析数据", notes = "获取分析数据")
    public R analysisData() {
        CommonDto2 commonDto2 = new CommonDto2();

        //region 比赛获奖情况分析
        List<AwardCompetitionDto> awardCompetitionDto = competitionRecordService.getTotalData();
        awardCompetitionDto.forEach(awardCompetition -> {
            String organizeOrganizer = competitionOrganizerService.selectByPrimaryKey(awardCompetition.getCompetitionInfoId()).getOrganizeOrganizer();
            awardCompetition.setCompetitionInfo(organizeOrganizer);
        });
        commonDto2.setCompReward(awardCompetitionDto);
        //endregion

        //region 积分排名
        List<UserInfo> userInfoList = userInfoService.getData();
        ArrayList<User> users = new ArrayList<>();
        userInfoList.forEach(item->{
            //构造user对象放入user集合中
            User user = new User();
            user=userService.selectByUserId(item.getUserId());
            user.setUserIntegral(item.getCreditsEarned());
            users.add(user);
        });
        List<User> collect = users.stream().sorted(Comparator.comparing(User::getUserIntegral).reversed()).collect(Collectors.toList());
        commonDto2.setStuRanking(collect);
        //endregion

        //region 总获奖总数
        List<CompetitionRecord> competitionRecords = competitionRecordService.selectAll();
        commonDto2.setSumReward(competitionRecords.size());
        //endregion

        //region 积分分发情况分析 & 总发放学分数量 （近一年）
        List<CreditDistributionDto> creditDistribution = new ArrayList<>();
        List<CompetitionApproval> ac = competitionApprovalService.selectAllSuccessApproval();
        Integer totalPointsIssuedInThePastYear = 0;
        for (CompetitionApproval item : ac) {
            boolean isExist = false;
            CreditDistributionDto creditDistributionDto = new CreditDistributionDto();
            creditDistributionDto.setCredit(Integer.valueOf(competitionRecordService.selectByPrimaryKey(item.getApplicantContentid()).getRecordApplyCredit()));
            creditDistributionDto.setTime(dateFormat(item.getUpdateTime(), "YYYY-MM"));
            totalPointsIssuedInThePastYear += creditDistributionDto.getCredit();
            for (CreditDistributionDto cdItem : creditDistribution) {
                if (Objects.equals(cdItem.getTime(), creditDistributionDto.getTime())) {
                    cdItem.setCredit(cdItem.getCredit() + creditDistributionDto.getCredit());
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                creditDistribution.add(creditDistributionDto);
            }
        }
        commonDto2.setCreditDistribution(creditDistribution);
        commonDto2.setSumCredit(totalPointsIssuedInThePastYear);
        //endregion

        return R.ok(commonDto2);
    }

}
