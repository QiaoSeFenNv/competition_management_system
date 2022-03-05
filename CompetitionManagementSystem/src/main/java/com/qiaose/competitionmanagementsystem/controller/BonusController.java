package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
import com.qiaose.competitionmanagementsystem.entity.CompetitionPrice;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.PriceDto;
import com.qiaose.competitionmanagementsystem.enums.BonusTypeEnum;
import com.qiaose.competitionmanagementsystem.enums.TodoStateEnum;
import com.qiaose.competitionmanagementsystem.service.ICompetitionBonusService;
import com.qiaose.competitionmanagementsystem.service.ICompetitionPriceService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: BonusController
 * @Description: 分配奖金
 * @Author qiaosefennv
 * @Date 2022/2/24
 * @Version 1.0
 */
@Slf4j
@RestController
@Api("比赛名称接口")
@Validated
@RequestMapping("/bonus")

public class BonusController {

    @Resource
    ICompetitionBonusService iCompetitionBonusService;

    @Resource
    ICompetitionPriceService iCompetitionPriceService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;



    @GetMapping("/bonusList")
    @ApiOperation(value = "获取所有获奖信息,可以根据奖项表的状态进行查询", notes = "分页查询")
    @Transactional(rollbackFor = {Exception.class})
    public R bonusList(@RequestParam(required = false) Byte status,
                       @RequestParam(defaultValue = "1", value = "page") Integer pageNo,
                       @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        //查询所有信息
        QueryWrapper<CompetitionBonus> queryWrapper = new QueryWrapper<>();
        if (status != null){
            queryWrapper.eq("status",status);
        }
        Page<CompetitionBonus> page = new Page<CompetitionBonus>(pageNo, pageSize);
        IPage<CompetitionBonus> pageList = iCompetitionBonusService.page(page, queryWrapper);
        PageDto<CompetitionBonus> pageDto = new PageDto<>();
        pageDto.setTotal((int)pageList.getTotal());
        pageDto.setItems(pageList.getRecords());
        return R.ok(pageDto);
    }

    @GetMapping("/bonusCur")
    @ApiOperation(value = "获取自身获奖信息", notes = "分页查询")
    @Transactional(rollbackFor = {Exception.class})
    public R bonusCur(HttpServletRequest request,
                       @RequestParam(defaultValue = "1", value = "page") Integer pageNo,
                       @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        String token = request.getHeader("Authorization");
        System.out.println(token);

        String userId = jwtTokenUtil.getUsernameFromToken(token);
        //查询所有信息
        QueryWrapper<CompetitionBonus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Page<CompetitionBonus> page = new Page<CompetitionBonus>(pageNo, pageSize);
        IPage<CompetitionBonus> pageList = iCompetitionBonusService.page(page, queryWrapper);

        pageList.getRecords().forEach(competitionBonus -> {
            CompetitionPrice byId = iCompetitionPriceService.getById(competitionBonus.getPriceId());
            competitionBonus.setCompetitionInfo(byId.getCompetitionInfo());
            competitionBonus.setCompetitionType(byId.getCompetitionType());
            competitionBonus.setTitleName(byId.getTitleName());
            competitionBonus.setCompetitionLevel(byId.getCompetitionLevel());
            competitionBonus.setOganizer(byId.getOganizer());
            competitionBonus.setCompetitionAward(byId.getCompetitionAward());
        });

        PageDto<CompetitionBonus> pageDto = new PageDto<>();
        pageDto.setTotal((int)pageList.getTotal());
        pageDto.setItems(pageList.getRecords());
        return R.ok(pageDto);
    }

    @PutMapping("/updateBonus")
    @ApiOperation(value = "学生填写获奖信息的其他信息", notes = "奖金默认不填，通过接口获取金额")
    @Transactional(rollbackFor = {Exception.class})
    public R updateBonus(@RequestBody  @Validated(CompetitionBonus.SecurityData.class)CompetitionBonus competitionBonus) {
        if (competitionBonus!=null) {
            //如果状态为已完成时则不可修改
            CompetitionBonus byId = iCompetitionBonusService.getById(competitionBonus.getId());
            if (byId == null) {
                return R.ok("无该数据");
            }
            if (Objects.equals(byId.getStatus(), BonusTypeEnum.FINISH.getCode())) {
                return R.failed("已完成不可修改");
            }
            competitionBonus.setStatus(BonusTypeEnum.IN_PROGRESS.getCode());
            iCompetitionBonusService.updateById(competitionBonus);
            //设置状态为已填写
            CompetitionPrice price = iCompetitionPriceService.getById(competitionBonus.getPriceId());
            price.setStatus(BonusTypeEnum.FILLED.getCode());
            iCompetitionPriceService.updateById(price);
        }

        return R.ok("");
    }


    @PutMapping("/sureBonus")
    @ApiOperation(value = "老师再次确认获奖信息,改变奖金使用状态", notes = "老师确认学生填写的信息，修改奖金表中的状态为奖金已发送")
    @Transactional(rollbackFor = {Exception.class})
    public R sureBonus(@RequestBody List<Integer> ids) {
        List<CompetitionBonus> competitionBonuses = iCompetitionBonusService.listByIds(ids);
        if (competitionBonuses.isEmpty()) {
            return R.ok("无该数据");
        }
        competitionBonuses.forEach(competitionBonus -> {
            competitionBonus.setStatus(TodoStateEnum.FINISH.getCode());
            CompetitionPrice price = iCompetitionPriceService.getById(competitionBonus.getPriceId());
            price.setStatus(BonusTypeEnum.FINISH.getCode());
            iCompetitionPriceService.updateById(price);
        });
        iCompetitionBonusService.updateBatchById(competitionBonuses);

        return R.ok("");
    }





}
