package com.qiaose.competitionmanagementsystem.configuration.auth;

import com.baomidou.mybatisplus.extension.api.R;

import com.qiaose.competitionmanagementsystem.service.SysFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleFrontendMenuTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

/**
 * 登录成功操作
 */
@Slf4j
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
        //只要token还在过期内，不需要每次重新生成
        //去redis中根据用户名找token
        String token = stringRedisTemplate.opsForValue().get(userDetails.getUsername());
        log.info("用户信息：账号名称：{},token：{},",userDetails.getUsername(),token);
        if(token ==null) {
            //如果token为空，则去创建一个新的token
            token = jwtTokenUtil.generateToken(userDetails);
            //将新的token存到redis中，并且设置token过期时间
            stringRedisTemplate.opsForValue().set(userDetails.getUsername(),token);
            stringRedisTemplate.expire(userDetails.getUsername(), Duration.ofDays(7));
        }

        //前端菜单获取放在api里了
        Map<String,Object> map = new HashMap<>();
        map.put("username",userDetails.getUsername());
        map.put("auth",userDetails.getAuthorities());
        map.put("token",token);
        //装入token
        R<Map<String,Object>> data = R.ok(map);
        //输出
        this.WriteJSON(request, response, data);

    }
}
