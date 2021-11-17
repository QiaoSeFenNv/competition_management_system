package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.User;

public interface UserService{


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByAccountName(String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    boolean checkLogin(String username, String password);
}
