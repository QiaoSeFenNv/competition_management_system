package com.qiaose.competitionmanagementsystem.components;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderUtil extends BCryptPasswordEncoder {

    /**
     * encode 对密码进行加密
     * @param rawPassword
     * @return  这个需要存入数据库中
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    /**
     * 判断密码是否正确
     * @param rawPassword           用户数据得密码
     * @param encodedPassword       可以是加密后进行判断，也可以从数据库获取进行判断
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return super.matches(rawPassword,encodedPassword);
    }

}
