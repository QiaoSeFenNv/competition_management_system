package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
import com.qiaose.competitionmanagementsystem.entity.CompetitionPrice;
import com.qiaose.competitionmanagementsystem.enums.TodoStateEnum;
import com.qiaose.competitionmanagementsystem.service.ICompetitionBonusService;
import com.qiaose.competitionmanagementsystem.service.ICompetitionPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PriceController
 * @Description: 奖金分配接口
 * @Author qiaosefennv
 * @Date 2022/2/24
 * @Version 1.0
 */
@Slf4j
@RestController
@Api("比赛名称接口")
@RequestMapping("/price")
public class PriceController {

    @Value("${tax}")
    private Double tax;

    @Resource
    ICompetitionPriceService iCompetitionPriceService;

    @Resource
    ICompetitionBonusService iCompetitionBonusService;
    //Todo 等待插入一条可以使用之后在批量导入



//    @PostMapping("/import")
//    @ApiOperation(value = "导入获奖信息", notes = "需要传入一个对象")
//    @Transactional(rollbackFor = {Exception.class})
//    public R importPriceInfo(@RequestBody List<CompetitionPrice> competitionPrice) {
//        //导入获奖信息
//        boolean b = iCompetitionPriceService.saveBatch(competitionPrice, competitionPrice.size());
//        //生成一条奖金分配信息    通过改条信息学生可以获得奖金的分配填写     均分
//
//        return R.ok("");
//    }

    @GetMapping("/priceList")
    @ApiOperation(value = "获取获奖信息", notes = "分页查询")
    @Transactional(rollbackFor = {Exception.class})
    public R priceList(@RequestParam(defaultValue = "1", value = "page") Integer pageNo,
                       @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        //查询所有信息
        QueryWrapper<CompetitionPrice> queryWrapper = new QueryWrapper<>();
        Page<CompetitionPrice> page = new Page<CompetitionPrice>(pageNo, pageSize);
        IPage<CompetitionPrice> pageList = iCompetitionPriceService.page(page, queryWrapper);
        return R.ok(pageList);
    }

    @PostMapping("/addPrice")
    @ApiOperation(value = "手动插入一条获奖信息", notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R addPrice(@RequestBody CompetitionPrice competitionPrice) {
        iCompetitionPriceService.save(competitionPrice);
        return R.ok("");
    }

    @DeleteMapping("/deletePrice")
    @ApiOperation(value = "删除多条获奖信息", notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R deletePrice(@RequestBody List<Integer> ids) {
        iCompetitionPriceService.removeByIds(ids);
        return R.ok("");
    }

    @PostMapping("/sendPrice")
    @ApiOperation(value = "发送获奖信息到指定学生上面", notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R sendPrice(@RequestBody List<Integer> ids) {
        List<CompetitionPrice> competitionPrices = iCompetitionPriceService.listByIds(ids);
        if (competitionPrices.isEmpty()){
            return R.ok("无该数据");
        }
        //生成一条奖项表
        competitionPrices.forEach(this::createBonus);
        return R.ok("");
    }

    @PostMapping("/updatePrice")
    @ApiOperation(value = "更新一条获奖信息", notes = "更新的时候希望把学号去掉")
    @Transactional(rollbackFor = {Exception.class})
    public R updatePrice(@RequestBody CompetitionPrice competitionPrice) {
        iCompetitionPriceService.updateById(competitionPrice);
        return R.ok("");
    }

    public void createBonus(CompetitionPrice competitionPrice){
        //根据学生数量生成对应数据
        String userId = competitionPrice.getUserId();
        String[] userIds = userId.split(",");
        String student = competitionPrice.getStudent();
        String[] students = student.split(",");
        for (int i = 0; i < userIds.length; i++) {
            CompetitionBonus competitionBonus = new CompetitionBonus();
            //设置 于其的管理属性好进行操作
            competitionBonus.setPriceId(competitionPrice.getId());
            //设置他的学号
            competitionBonus.setUserId(userIds[i]);
            competitionBonus.setUserName(students[i]);
            //分配奖金
            competitionBonus.setBonus(competitionPrice.getMoney()/ userIds.length);
            competitionBonus.setState(TodoStateEnum.NOT_START.getCode());
            //插入表中
            iCompetitionBonusService.save(competitionBonus);
        }

    }


}
