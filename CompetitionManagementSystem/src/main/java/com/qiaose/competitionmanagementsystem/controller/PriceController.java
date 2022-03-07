package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.CompetitionBonus;
import com.qiaose.competitionmanagementsystem.entity.CompetitionPrice;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.entity.dto.PageDto;
import com.qiaose.competitionmanagementsystem.entity.dto.PriceDto;
import com.qiaose.competitionmanagementsystem.entity.dto.PriceDto2;
import com.qiaose.competitionmanagementsystem.entity.dto.StudentDto;
import com.qiaose.competitionmanagementsystem.enums.BonusTypeEnum;
import com.qiaose.competitionmanagementsystem.enums.TodoStateEnum;
import com.qiaose.competitionmanagementsystem.exception.TipException;
import com.qiaose.competitionmanagementsystem.service.ICompetitionBonusService;
import com.qiaose.competitionmanagementsystem.service.ICompetitionPriceService;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
    UserInfoService userInfoService;

    @Resource
    ICompetitionBonusService iCompetitionBonusService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping("/import")
    @ApiOperation(value = "导入获奖信息", notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R importPriceInfo(@RequestBody List<PriceDto2> priceDtoList) {
        ArrayList<CompetitionPrice> priceArrayList = new ArrayList<>();

        priceDtoList.forEach(priceDto -> {
            CompetitionPrice competitionPrice = new CompetitionPrice();
            log.info("{}",priceDto);
            //讲dto变为competitionPrice
            BeanUtils.copyProperties(priceDto,competitionPrice);
            log.info("{}",competitionPrice);
            competitionPrice.setAwardTime(DateUtil.parse(priceDto.getAwardTime(),"yyyy.mm"));
            competitionPrice.setUserId(getString(priceDto.getStudentDtoList()));
            competitionPrice.setStatus(BonusTypeEnum.NOT_START.getCode());
            priceArrayList.add(competitionPrice);
        });

        //导入获奖信息
        boolean b = iCompetitionPriceService.saveBatch(priceArrayList);
        //生成一条奖金分配信息    通过改条信息学生可以获得奖金的分配填写     均分

        return R.ok("");
    }

    public static String getString(List<StudentDto> studentDtoList){
        StringBuilder stringBuilder = new StringBuilder();
        studentDtoList.forEach(studentDto -> {
            if (studentDto.getUserId() != null){
                stringBuilder.append(studentDto.getUserId()+",");
            }

        });
        return String.valueOf(stringBuilder);
    }

    @GetMapping("/priceList")
    @ApiOperation(value = "获取获奖信息", notes = "分页查询")
    @Transactional(rollbackFor = {Exception.class})
    public R priceList(@RequestParam(defaultValue = "1", value = "page") Integer pageNo,
                       @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {

        //查询所有信息
        QueryWrapper<CompetitionPrice> queryWrapper = new QueryWrapper<>();
        Page<CompetitionPrice> page = new Page<CompetitionPrice>(pageNo, pageSize);
        IPage<CompetitionPrice> pageList = iCompetitionPriceService.page(page, queryWrapper);
        log.info("奖金数据：{}", pageList.getRecords());

        List<PriceDto> priceDtoList = new ArrayList<>();

        pageList.getRecords().forEach(competitionPrice -> {

            PriceDto priceDto = new PriceDto();
            BeanUtils.copyProperties(competitionPrice, priceDto);
            priceDto.setAwardTime(competitionPrice.getAwardTime().getTime());

            String[] userIds = competitionPrice.getUserId().split(",");
            List<StudentDto> studentDtoList = new ArrayList<>();
            for (String userId : userIds) {
                String username = "";
                if (userInfoService.selectByWorkId(userId) != null) {
                    username = userInfoService.selectByWorkId(userId).getUserName();
                }
                studentDtoList.add(new StudentDto(username, userId));
            }
            //改变学生
            priceDto.setStudentDtoList(studentDtoList);
            priceDtoList.add(priceDto);
        });

        PageDto<PriceDto> pageDto = new PageDto<>();
        pageDto.setTotal((int) pageList.getTotal());
        pageDto.setItems(priceDtoList);
        return R.ok(pageDto);
    }

    @GetMapping("/getDetailInfo")
    @ApiOperation(value = "根据id获取", notes = "")
    @Transactional(rollbackFor = {Exception.class})
    public R getDetailInfo(@RequestParam Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println(token);

        String workId = jwtTokenUtil.getUsernameFromToken(token);

        List<PriceDto> priceDtoList = new ArrayList<>();

        CompetitionPrice competitionPrice = iCompetitionPriceService.getById(id);

        PriceDto priceDto = new PriceDto();
        BeanUtils.copyProperties(competitionPrice, priceDto);
        priceDto.setAwardTime(competitionPrice.getAwardTime().getTime());
        //获取到bonus数据根据price以及userId
        QueryWrapper<CompetitionBonus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("price_id", competitionPrice.getId());
        queryWrapper.eq("user_id",workId);
        priceDto.setCompetitionBonus(iCompetitionBonusService.getOne(queryWrapper));

        String[] userIds = competitionPrice.getUserId().split(",");
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (String userId : userIds) {
            studentDtoList.add(new StudentDto(userInfoService.selectByWorkId(userId).getUserName(), userId));
        }
        //改变学生
        priceDto.setStudentDtoList(studentDtoList);
        priceDtoList.add(priceDto);

        PageDto<PriceDto> pageDto = new PageDto<>();
        pageDto.setItems(priceDtoList);

        return R.ok(pageDto);
    }

    @PostMapping("/addPrice")
    @ApiOperation(value = "手动插入一条获奖信息", notes = "需要传入一个对象")
    @Transactional(rollbackFor = {Exception.class})
    public R addPrice(@RequestBody CompetitionPrice competitionPrice) {
        competitionPrice.setStatus(BonusTypeEnum.NOT_START.getCode());
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
        if (competitionPrices.isEmpty()) {
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

    public String createBonus(CompetitionPrice competitionPrice) {
        //根据学生数量生成对应数据
        String userId = competitionPrice.getUserId();
        QueryWrapper<CompetitionBonus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", competitionPrice.getId());
        List<CompetitionBonus> list = iCompetitionBonusService.list(queryWrapper);
        if (!list.isEmpty()) {
            throw new TipException("重复发送");
        }
        competitionPrice.setStatus(BonusTypeEnum.IN_PROGRESS.getCode());
        iCompetitionPriceService.updateById(competitionPrice);

        String[] userIds = userId.split(",");
        for (int i = 0; i < userIds.length; i++) {
            CompetitionBonus competitionBonus = new CompetitionBonus();
            //设置 于其的管理属性好进行操作
            competitionBonus.setPriceId(competitionPrice.getId());
            //设置他的学号
            competitionBonus.setUserId(userIds[i]);
            competitionBonus.setUserName(userInfoService.selectByWorkId(userIds[i]).getUserName());
            //分配奖金
            competitionBonus.setBonus(competitionPrice.getMoney() / userIds.length);
            competitionBonus.setStatus(TodoStateEnum.IN_PROGRESS.getCode());
            //插入表中
            iCompetitionBonusService.save(competitionBonus);
        }
        return "";
    }

}
