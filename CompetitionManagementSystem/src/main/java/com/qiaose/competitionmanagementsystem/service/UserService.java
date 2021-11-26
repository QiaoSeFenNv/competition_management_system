package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.dto.UserDto;

import java.util.List;

public interface UserService{


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByAccountName(String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    boolean checkLogin(String username, String password) throws Exception;

    List<User> list();

    UserDto PoToDto(User user);

    User U_DtoToPo(UserDto userDto);

    Boolean register(User user) throws Exception;

    List<User> findUser(User user);
}
