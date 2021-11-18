package com.qiaose.competitionmanagementsystem.configuration.auth;

import com.baomidou.mybatisplus.extension.api.R;

import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleFrontendMenuTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录成功操作
 */
@Component
public class MyAuthenticationSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler {

    @Autowired
    SysFrontendMenuTableService sysFrontendMenuTableService;

    @Autowired
    SysRoleFrontendMenuTableService sysRoleFrontendMenuTableService;

    @Autowired
    UserService userService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //取得账号信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //
        System.out.println("userDetails = " + userDetails);
        //取token
        //好的解决方案，登录成功后token存储到数据库中
        //只要token还在过期内，不需要每次重新生成
        //先去缓存中找

//        String token = TokenCache.getTokenFromCache(userDetails.getUsername());

        String token = stringRedisTemplate.opsForValue().get(userDetails.getUsername());
        System.out.println(token);

        if(token ==null) {
            System.out.println("初次登录，token还没有，生成新token。。。。。。");
            //如果token为空，则去创建一个新的token
//            jwtTokenUtil = new JwtTokenUtil();
            token = jwtTokenUtil.generateToken(userDetails);
            //把新的token存储到缓存中
//            TokenCache.setToken(userDetails.getUsername(),token);
            stringRedisTemplate.opsForValue().set(userDetails.getUsername(),token);
            stringRedisTemplate.expire(userDetails.getUsername(), Duration.ofDays(7));
        }


//        //通用户表获得角色号
//        User user = userService.selectByAccountName(userDetails.getUsername());
//        String roleId = user.getRoleId();
//        //通过中间表来获取前端菜单id
//        SysRoleFrontendMenuTable sysRoleFrontendMenuTable = sysRoleFrontendMenuTableService.selectByRoleId(roleId);
//        //加载前端菜单
//        List<SysFrontendMenuTable> menus = sysFrontendMenuTableService.selectByPrimaryKey(sysRoleFrontendMenuTable.getFrontendMenuId());
//        //
        Map<String,Object> map = new HashMap<>();
        map.put("username",userDetails.getUsername());
        map.put("auth",userDetails.getAuthorities());
//        map.put("menus",menus);
        //装入token
        R<Map<String,Object>> data = R.ok(map);
        //输出
        this.WriteJSON(request, response, data,token);

    }
}
