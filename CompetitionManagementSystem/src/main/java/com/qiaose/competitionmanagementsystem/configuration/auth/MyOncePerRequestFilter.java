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
import java.rmi.server.ExportException;

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
        System.out.println("headerToken = " + headerToken);
        System.out.println("request getMethod = " + request.getMethod());


        if (!StringUtil.isNullOrEmpty(headerToken)) {
            //前端传来的token
            String olDToken = headerToken.replace("Bearer", "").trim();
            String userId = jwtTokenUtil.getUsernameFromToken(olDToken);
            //redis中的token
            String token = stringRedisTemplate.opsForValue().get("Token"+userId);

            /*提交失败*/
            if (token == null){
                throw  new ServletException("令牌过期,请重新登录");
            }
            //判断令牌是否过期，默认是一周
            //比较好的解决方案是：
            //登录成功获得token后，将token存储到数据库（redis）
            //将数据库版本的token设置过期时间为15~30分钟
            //如果数据库中的token版本过期，重新刷新获取新的token
            //注意：刷新获得新token是在token过期时间内有效。
            //如果token本身的过期（1周），强制登录，生成新token。
            boolean check = false;

            try {
                check = this.jwtTokenUtil.isTokenExpired(olDToken);
            } catch (Exception e) {
                new Throwable("登录已过期，请重新登录。"+e.getMessage());
            }

            if (!check){

//                String userId = jwtTokenUtil.getUsernameFromToken(olDToken);
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
