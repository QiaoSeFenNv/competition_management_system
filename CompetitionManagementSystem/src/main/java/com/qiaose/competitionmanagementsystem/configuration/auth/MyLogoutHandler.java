package com.qiaose.competitionmanagementsystem.configuration.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出Handler  前端会发起logout请求，携带请求头
 */
@Component
public class MyLogoutHandler extends JSONAuthentication implements LogoutHandler {
    private String header = "Authorization";



    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String headerToken = request.getHeader(header);
        if (!StringUtils.isEmpty(headerToken)) {

            //postMan测试时，自动加入的前缀，要去掉。
            String token = headerToken.replace("Bearer", "").trim();
            //清楚全局缓存
            SecurityContextHolder.clearContext();
        }
    }
}
