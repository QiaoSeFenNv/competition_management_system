package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.CompetitionAward;
import com.qiaose.competitionmanagementsystem.entity.CompetitionAwardName;
import com.qiaose.competitionmanagementsystem.entity.CompetitionInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.CompetitionAwardDto;
import com.qiaose.competitionmanagementsystem.enums.RecordTypeEnum;
import com.qiaose.competitionmanagementsystem.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: AwardRecordController
 * @Description: 奖项记录
 * @Author qiaosefennv
 * @Date 2022/2/21
 * @Version 1.0
 */
@RestController
@Api("奖项记录")
@RequestMapping("/awardRecord")
public class AwardRecordController {
    @Resource
    ICompetitionAwardService iCompetitionAwardService;

    @Resource
    ICompetitionAwardNameService iCompetitionAwardNameService;

    @Resource
    CompetitionInfoService competitionInfoService;

    @Resource
    CompetitionOrganizerService competitionOrganizerService;

    @Resource
    CompetitionRewardService competitionRewardService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @GetMapping("/getCurAward")
    @ApiOperation(value = "获得当前对象的奖项记录",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R getCurAward(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println(token);
        String userId = jwtTokenUtil.getUsernameFromToken(token);

        CompetitionAwardDto competitionAwardDto = new CompetitionAwardDto();
        QueryWrapper<CompetitionAward> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",userId);
        QueryWrapper<CompetitionAwardName> queryWrapper2 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",userId);
        //添加额外字段
        List<CompetitionAward> competitionAwards = addFiles(iCompetitionAwardService.list(queryWrapper1));
        List<CompetitionAwardName> competitionAwardNames = iCompetitionAwardNameService.list(queryWrapper2);
        competitionAwardDto.setCompetitionAwardList(competitionAwards);
        competitionAwardDto.setCompetitionAwardNameList(competitionAwardNames);

        return R.ok(competitionAwardDto);
    }

    @GetMapping("/getAwardById")
    @ApiOperation(value = "获得奖项通过用户id查询",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R getAwardById(@RequestParam String userId){
        CompetitionAwardDto competitionAwardDto = new CompetitionAwardDto();
        QueryWrapper<CompetitionAward> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",userId);
        QueryWrapper<CompetitionAwardName> queryWrapper2 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",userId);
        //添加字段
        List<CompetitionAward> competitionAwards = addFiles(iCompetitionAwardService.list(queryWrapper1));
        List<CompetitionAwardName> competitionAwardNames = iCompetitionAwardNameService.list(queryWrapper2);
        competitionAwardDto.setCompetitionAwardList(competitionAwards);
        competitionAwardDto.setCompetitionAwardNameList(competitionAwardNames);
        return R.ok(competitionAwardDto);
    }


    @PostMapping("insertBatch")
    @ApiOperation(value = "批量导入插入一条信息",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R insert(@RequestBody List<CompetitionAwardName> competitionAwardName){
        competitionAwardName.forEach(v->{
            v.setRecordType(RecordTypeEnum.BATCH_IMPORT.getCode());
        });
        iCompetitionAwardNameService.saveBatch(competitionAwardName);
        return R.ok("");
    }


    @PostMapping("insert")
    @ApiOperation(value = "手动插入一条信息",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R insert(@RequestBody CompetitionAward competitionAward){
        competitionAward.setRecordType(RecordTypeEnum.MANUAL_IMPORT.getCode());
        iCompetitionAwardService.save(competitionAward);
        return R.ok("");
    }

    @PostMapping("update")
    @ApiOperation(value = "手动更新一条信息",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R update(@RequestBody CompetitionAward competitionAward){

        iCompetitionAwardService.updateById(competitionAward);
        return R.ok("");
    }

    @PostMapping("delete")
    @ApiOperation(value = "手动删除一条信息",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R delete(@RequestParam Long id){
        QueryWrapper<CompetitionAward> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        queryWrapper.eq("record_type",2);
        boolean remove = iCompetitionAwardService.remove(queryWrapper);
        if (remove) {
            return R.failed("只能删除手动导入信息");
        }
        return R.ok("");
    }


    @PostMapping("deleteAward")
    @ApiOperation(value = "excel批量信息",notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R deleteAward(@RequestParam List<Long> id){

        boolean remove = iCompetitionAwardNameService.removeByIds(id);
        if (remove) {
            return R.failed("只能删除手动导入信息");
        }
        return R.ok("");
    }


    public List<CompetitionAward>  addFiles(List<CompetitionAward> competitionAwards){
        competitionAwards.forEach(v->{
            CompetitionInfo competitionInfo = competitionInfoService.selectByPrimaryKey(v.getCompId());
            if (competitionInfo != null) {
                v.setCompName(competitionInfo.getName());
                v.setOrganizeName(
                        competitionOrganizerService.selectByPrimaryKey(competitionInfo.getOrganizerId()).getOrganizeName());
                v.setRewardLevelName(competitionRewardService.selectByPrimaryKey(v.getRewardLevel()).getRewardLevel());
            }
        });
        return competitionAwards;
    }
}
