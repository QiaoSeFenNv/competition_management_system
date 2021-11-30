package com.qiaose.competitionmanagementsystem.service.auth;


import com.qiaose.competitionmanagementsystem.entity.SysRoleTable;
import com.qiaose.competitionmanagementsystem.entity.SysRoleUserTable;
import com.qiaose.competitionmanagementsystem.entity.User;
import com.qiaose.competitionmanagementsystem.service.SysRoleTableService;
import com.qiaose.competitionmanagementsystem.service.SysRoleUserTableService;
import com.qiaose.competitionmanagementsystem.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 要实现UserDetailsService接口，这个接口是security提供的
 */
@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SysRoleTableService sysRoleTableService;

    @Autowired
    private SysRoleUserTableService sysRoleUserTableService;

    /**
     * 通过账号查找用户、角色的信息
     * @param accountName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        //查出账号用户
        User user = userService.selectByAccountName(accountName);
        //判断用户是否存在
        if (user == null){
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", accountName));
        }else{
            //从用户中获得角色号（这里是等于中间表的userid字段）
            String userId = user.getRoleId();
            //通过角色号从RoleUser中间表中查出角色号
            List<SysRoleUserTable> sysRoleUserTable = sysRoleUserTableService.selectByRoleId(userId);
            List<SysRoleTable> sysRoleTables = new ArrayList<>();
            //获取正真的角色号
            for (SysRoleUserTable roleUserTable : sysRoleUserTable) {
                List<SysRoleTable> sysRoleTable = sysRoleTableService.selectByPrimaryKey(roleUserTable.getRoleId());
                for (SysRoleTable roleTable : sysRoleTable) {
                    sysRoleTables.add(roleTable);
                }
            }
            //从角色表中获取角色（一个用户可以有多个角色）

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            //判断账号角色
            for (SysRoleTable sysRoleTable : sysRoleTables) {
                authorities.add(new SimpleGrantedAuthority(sysRoleTable.getRoleName()));
            }

            System.out.println("loadUserByUsername......user ===> " + user);
            return new AuthUser(user.getAccountName(), user.getPassword(),authorities);
        }
    }
}
