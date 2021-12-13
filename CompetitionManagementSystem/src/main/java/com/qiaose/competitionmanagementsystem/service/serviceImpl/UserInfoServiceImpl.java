package com.qiaose.competitionmanagementsystem.service.serviceImpl;

import com.qiaose.competitionmanagementsystem.entity.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;
import com.qiaose.competitionmanagementsystem.mapper.UserInfoMapper;
import com.qiaose.competitionmanagementsystem.service.UserInfoService;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserInfo record) {
        return userInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(UserInfo record) {
        return userInfoMapper.insertSelective(record);
    }

    @Override
    public UserInfo selectByPrimaryKey(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserInfo record) {
        return userInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserInfo record) {
        return userInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UserInfo> selectByDeptId(String s) {
        return userInfoMapper.selectByDeptId(s);
    }

    @Override
    public UserInfo selectByWorkId(String workId) {
        return userInfoMapper.selectByWorkId(workId);
    }

    @Override
    public UserInfo selectByEmail(String email) {
        return userInfoMapper.selectByEmail(email);
    }

}
