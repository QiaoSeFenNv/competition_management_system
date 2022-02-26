package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.User;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

public interface UserService{


    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);


    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserId(String userId);

    boolean checkLogin(String username, String password)throws Exception;

    List<User> getAllUser();

    int deleteByUserId(String userId);
}
