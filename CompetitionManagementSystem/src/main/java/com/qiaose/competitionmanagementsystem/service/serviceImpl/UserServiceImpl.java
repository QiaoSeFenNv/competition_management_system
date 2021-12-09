package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.qiaose.competitionmanagementsystem.components.BCryptPasswordEncoderUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.mapper.UserMapper;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Resource
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Override
    public int deleteByPrimaryKey(Long id) {
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
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public boolean checkLogin(String username, String password)throws Exception {
        User user = userMapper.selectByUserId(username);

        if (user == null) {
            throw new Exception("账号不存在");
        }else{
            if (!bCryptPasswordEncoderUtil.matches(password,user.getUserPassword())) {
                throw new Exception("密码不正确");
            }else{
                return true;
            }
        }
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}
