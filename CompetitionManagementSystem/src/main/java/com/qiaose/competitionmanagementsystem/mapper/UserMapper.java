package com.qiaose.competitionmanagementsystem.mapper;

import com.qiaose.competitionmanagementsystem.entity.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);


    /**
     * 传入User对象插入所有字段
     * @param record
     * @return
     */
    int insert(User record);
    /**
     * 传入User对象插入存在的字段
     * @param record
     * @return
     */
    int insertSelective(User record);



    User selectByPrimaryKey(Integer id);


    User selectByAccountName(String name);


    List<User> findUser(User record);

    List<User> selectByAll();

    /**
     * 传入User对象修改存在的字段
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 传入User对象修改所有字段
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);




}