package com.qiaose.competitionmanagementsystem.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CompetitionAttach;
import com.qiaose.competitionmanagementsystem.entity.CompetitionSlideshow;
import com.qiaose.competitionmanagementsystem.service.CompetitionAttachService;
import com.qiaose.competitionmanagementsystem.service.CompetitionSlideshowService;
import com.qiaose.competitionmanagementsystem.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(value = "轮播图")
@RequestMapping("/slideshow")
public class SlidesController {


    @Autowired
    CompetitionSlideshowService competitionSlideshowService;

    @Autowired
    CompetitionAttachService competitionAttachService;


    public static final String CLASSPATH = TaleUtils.getUplodFilePath();

    @GetMapping("/getSlide")
    @ApiOperation(value = "获得所有轮播图", notes = "需要前端传递一个body")
    public R getSlides(){
        List<CompetitionSlideshow> competitionSlideshows = competitionSlideshowService.selectAll();
        return R.ok(competitionSlideshows);
    }


    @PostMapping("/insert")
    @ApiOperation(value = "插入轮播图片", notes = "需要前端传递一个body,文件")
    @Transactional(rollbackFor = Exception.class)
    public R insertSlides(@RequestBody CompetitionSlideshow competitionSlideshow
        , @RequestParam(required = false ,value = "multipartFiles") MultipartFile[] multipartFiles){

        if (multipartFiles != null){
            for (MultipartFile multipartFile : multipartFiles) {
                String fname = multipartFile.getOriginalFilename();
                //判断文件类是是否为图片 fname为上传文件名
                int index = fname.lastIndexOf(".");
                String suffix = null;
                if (index == -1 || (suffix = fname.substring(index + 1)).isEmpty()) {
                    return R.failed("文件后缀不能为空");
                }
                //记录可以上传文件类型种类
                Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
                if (!allowSuffix.contains(suffix.toLowerCase())) {
                    return R.failed("非法的文件，不允许的文件类型：" + suffix);
                }
                String fkey = TaleUtils.getSlide(fname);
                String ftype = "slide";
                CompetitionAttach competitionAttach = new CompetitionAttach();
                competitionAttach.setFkey(fkey);
                competitionAttach.setFname(fname);
                competitionAttach.setFtype(ftype);
                competitionAttach.setUserId(0);
                competitionAttach.setCreated((int) (System.currentTimeMillis() / 1000));
                File file = new File(CLASSPATH + fkey);
                try {
                    FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                competitionAttachService.insertSelective(competitionAttach);
                competitionSlideshow.setPath(ftype);
            }
        }


        competitionSlideshow.setCreateTime(DateUtil.date());
        int i = competitionSlideshowService.insertSelective(competitionSlideshow);
        if (i<=0) {
            return R.failed("插入失败");
        }

        return R.ok(competitionSlideshow);

    }


    @PostMapping("/update")
    @ApiOperation(value = "更新轮播图片", notes = "需要前端传递一个body，需要附带id值")
    @Transactional(rollbackFor = Exception.class)
    public R updateSlides(@RequestBody CompetitionSlideshow competitionSlideshow ){
        int i = competitionSlideshowService.updateByPrimaryKeySelective(competitionSlideshow);
        if (i<=0) {
            return R.failed("更新失败");
        }
        return R.ok("更新成功");
    }



    /* 以后都是使用post*/
    @PostMapping("/delete")
    @ApiOperation(value = "删除轮播图片", notes = "需要前端传递一个id值")
    @Transactional(rollbackFor = Exception.class)
    public R deleteSlides(@RequestBody CompetitionSlideshow competitionSlideshow ){
        final Integer id = competitionSlideshow.getId();
        int i = competitionSlideshowService.deleteByPrimaryKey(id);
        if (i<=0) {
            return R.failed("删除失败");
        }
        return R.ok("删除成功");
    }

}
