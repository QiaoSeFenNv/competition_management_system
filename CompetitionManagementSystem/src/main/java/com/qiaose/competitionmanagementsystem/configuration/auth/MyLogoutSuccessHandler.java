package com.qiaose.competitionmanagementsystem.configuration.auth;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.service.auth.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 成功退出处理器
 */

@Slf4j
@Component
public class MyLogoutSuccessHandler extends JSONAuthentication implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        AuthUser principal = (AuthUser)authentication.getPrincipal();
        log.info("用户：{}退出",principal.getUsername());
        R<String> data = R.ok("退出成功");
        super.WriteJSON(request,response,data);
    }
}
