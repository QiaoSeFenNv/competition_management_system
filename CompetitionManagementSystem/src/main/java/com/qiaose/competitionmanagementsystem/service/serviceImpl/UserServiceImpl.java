package com.qiaose.competitionmanagementsystem.service.serviceImpl;


import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.mapper.UserMapper;
import com.qiaose.competitionmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;


    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByAccountName(String name) {
        return userMapper.selectByAccountName(name);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean checkLogin(String username, String password) throws Exception{
        User user = userMapper.selectByAccountName(username);
        if (user == null) {
            throw new Exception("账号不存在，请重新登录");
        }else{
            String encodedPassword = user.getPassword();
            if (!bCryptPasswordEncoderUtil.matches(password,encodedPassword)){
                throw new Exception("密码不正确");
                //System.out.println("checkLogin--------->密码不正确！");
                //设置友好提示
            }else{
                return true;
            }
        }
    }

    @Override
    public List<User> list() {
        return userMapper.selectByAll();
    }

    @Override
    public Object register(User user) {
        return null;
    }

}
