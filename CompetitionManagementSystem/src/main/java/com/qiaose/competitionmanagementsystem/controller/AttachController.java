package com.qiaose.competitionmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.entity.CompetitionAttach;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.UserService;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import com.qiaose.competitionmanagementsystem.service.CompetitionAttachService;
import com.qiaose.competitionmanagementsystem.utils.FileTypeUtil;
import com.qiaose.competitionmanagementsystem.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/attach")
@Api(value = "文件接口")
public class AttachController {


    @Autowired
    CompetitionAttachService competitionAttachService;

    @Autowired
    UserService userService;

    @PostMapping(value = "/upload")
    @ResponseBody
    @ApiOperation(value = "上传附件", notes = "限制文件")
    @Transactional(rollbackFor = Exception.class)
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String suffix = null;
        User user = null;
        if (principal instanceof UserDetails) {
            AuthUser authUser = (AuthUser) principal;
            user = userService.selectByUserId(authUser.getUsername());
        }
        if (user == null) {
            return R.failed("用户信息错误");
        }
        Long uid = user.getId();
        //文件上传
        for (MultipartFile multipartFile : multipartFiles) {
            //文件原本名称
            String fname = multipartFile.getOriginalFilename();
            //判断文件类是是否为图片 fname为上传文件名
            int index = fname.lastIndexOf(".");

            if (index == -1 || (suffix = fname.substring(index + 1)).isEmpty()) {
                return R.failed("文件后缀不能为空");
            }

            List<CompetitionAttach> listAttach = new ArrayList<>();
            if (FileTypeUtil.imageType(suffix.toLowerCase()) || FileTypeUtil.fileType(suffix.toLowerCase())){
                //附件得类型无论是图片还是文件都为file
                String ftype = "file";
                //获得文件路径
                String fkey = TaleUtils.getFileKey(fname,request.getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/static");
                //将数据存放到competitionAttach对象中
                CompetitionAttach competitionAttach = new CompetitionAttach();
                competitionAttach.setFkey(fkey);
                competitionAttach.setFname(fname);
                competitionAttach.setFtype(ftype);
                competitionAttach.setUserId(uid);
                competitionAttach.setCreated((int) (System.currentTimeMillis() / 1000));
                //在指定路径存放文件
                String realPath=request.getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/static";
                File file = new File(realPath + fkey);
                try {
                    FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                competitionAttachService.insertSelective(competitionAttach);
                listAttach.add(competitionAttach);
                System.out.println("成功上传且写入数据");

            }
            return R.ok(listAttach);
        }

        return R.failed("非法的文件，不允许的文件类型："+suffix);
    }

    @PostMapping(value = "/images")
    @ResponseBody
    @ApiOperation(value = "上传图片", notes = "限制文件上传种类")
    @Transactional(rollbackFor = Exception.class)
    public R uploadImages(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof UserDetails) {
            AuthUser authUser = (AuthUser) principal;
            user = userService.selectByUserId(authUser.getUsername());
        }
        if (user == null) {
            return R.failed("用户信息错误");
        }
        Long uid = user.getId();
        List<CompetitionAttach> listAttach = new ArrayList<>();
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
            //获得文件的路径
            String fkey = TaleUtils.getImagesKey(fname,request.getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/static");
            //设置类型
            String ftype = "image";
            CompetitionAttach competitionAttach = new CompetitionAttach();
            competitionAttach.setFkey(fkey);
            competitionAttach.setFname(fname);
            competitionAttach.setFtype(ftype);
            competitionAttach.setUserId(uid);
            competitionAttach.setCreated((int) (System.currentTimeMillis() / 1000));
            //上传到真实路径上
            String realPath=request.getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/static";
            File file = new File(realPath + fkey);
            try {
                FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            competitionAttachService.insertSelective(competitionAttach);
            listAttach.add(competitionAttach);

        }
        return R.ok(listAttach);
    }



    @PostMapping(value = "/imagesAva")
    @ResponseBody
    @ApiOperation(value = "上传头像", notes = "限制文件上传种类，用户上传得头像")
    @Transactional(rollbackFor = Exception.class)
    public R uploadImageAva(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof UserDetails) {
            AuthUser authUser = (AuthUser) principal;
            user = userService.selectByUserId(authUser.getUsername());
        }
        if (user == null) {
            return R.failed("用户信息错误");
        }
        Long uid = user.getId();
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
            String fkey = TaleUtils.getImagesKey(fname,request.getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/static");
            String ftype = "image-ava";
            CompetitionAttach competitionAttach = new CompetitionAttach();
            competitionAttach.setFkey(fkey);
            competitionAttach.setFname(fname);
            competitionAttach.setFtype(ftype);
            competitionAttach.setUserId(uid);
            competitionAttach.setCreated((int) (System.currentTimeMillis() / 1000));
            user.setUserAvatarurl(fkey);
            //SSS
            String realPath=request.getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/static";
            File file = new File(realPath + fkey);
            try {
                FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            competitionAttachService.insertSelective(competitionAttach);
            userService.updateByPrimaryKeySelective(user);
            return R.ok(competitionAttach);
        }
        return R.ok("上传失败");

    }
}
