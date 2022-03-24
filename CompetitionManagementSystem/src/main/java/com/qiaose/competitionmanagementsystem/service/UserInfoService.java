package com.qiaose.competitionmanagementsystem.service;

import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.entity.UserInfo;

import java.util.List;

public interface UserInfoService{


    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByUserSelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> selectByDeptId(String s);

    UserInfo selectByWorkId(String workId);

    UserInfo selectByEmail(String email);

    List<UserInfo> selectByName(String name);

    List<UserInfo> selectByUserSelect(String userSelect);

    List<UserInfo> selectByUserCredit(String userSelect);
}
