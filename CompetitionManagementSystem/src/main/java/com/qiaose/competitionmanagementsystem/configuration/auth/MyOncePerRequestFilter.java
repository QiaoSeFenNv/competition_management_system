package com.qiaose.competitionmanagementsystem.configuration.auth;



import com.qiaose.competitionmanagementsystem.components.JwtTokenUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器
 *
 */
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    /**
     * 登录信息接口
     */
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * jwtToken工具
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    /**
     * 认证头信息
     */
    private String header = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String headerToken = request.getHeader(header);


        if (!StringUtil.isNullOrEmpty(headerToken)) {
            //前端传来的token
            String olDToken = headerToken.replace("Bearer", "").trim();
            boolean check = false;

            try {
                check = this.jwtTokenUtil.isTokenExpired(olDToken);
            } catch (Exception e) {
                new Throwable("令牌已过期，请重新登录。"+e.getMessage());
            }

            if (!check){

                String userId = jwtTokenUtil.getUsernameFromToken(olDToken);
                System.out.println("username = " + userId);

                //判断用户不为空，且SecurityContextHolder授权信息还是空的
                if ( userId!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //通过用户信息得到UserDetails
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                    //验证令牌有效性
                    boolean validata = false;
                    try {
                        validata = jwtTokenUtil.validateToken(olDToken, userDetails);
                    }catch (Exception e) {
                        new Throwable("验证token无效:"+e.getMessage());
                    }

                    if (validata) {
                        // 将用户信息存入 authentication，方便后续校验
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        //
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
