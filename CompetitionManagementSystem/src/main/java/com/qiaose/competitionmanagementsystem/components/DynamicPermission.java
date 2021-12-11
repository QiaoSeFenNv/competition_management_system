package com.qiaose.competitionmanagementsystem.components;



import com.qiaose.competitionmanagementsystem.configuration.auth.MyaccessDeniedException;
import com.qiaose.competitionmanagementsystem.entity.admin.SysBackendApiTable;
import com.qiaose.competitionmanagementsystem.entity.admin.SysRoleBackendApiTable;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysBackendApiTableService;
import com.qiaose.competitionmanagementsystem.service.adminImpl.SysRoleBackendApiTableService;
import com.qiaose.competitionmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DynamicPermission {

    @Autowired
    SysBackendApiTableService sysBackendApiTableService;

    @Autowired
    SysRoleBackendApiTableService sysRoleBackendApiTableService;

    @Autowired
    UserService userService;


    /**
     * 判断有访问API的权限
     * @param request
     * @param authentication
     * @return
     * @throws MyaccessDeniedException
     */
    public boolean checkPermisstion(HttpServletRequest request,
                                   Authentication authentication) throws MyaccessDeniedException {

        Object principal = authentication.getPrincipal();
        System.out.println("DynamicPermission principal = " + principal);

        if(principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;

            List<List<SysRoleBackendApiTable>> sysRoleBackendApiTable = new ArrayList<>();
            //先难道当前用户的所有角色id
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                //它的权限角色id
                String roleId = authority.getAuthority();
                sysRoleBackendApiTable.add(sysRoleBackendApiTableService.selectByRoleId(roleId));
            }


            //通过账号获取资源鉴权
            List<SysBackendApiTable> apiUrls = new ArrayList<>();
            for (List<SysRoleBackendApiTable> sysRoleBackendApiTables : sysRoleBackendApiTable) {
                for (SysRoleBackendApiTable roleBackendApiTable : sysRoleBackendApiTables) {
                    //将所有的路径都取出来放进apiUrl里面
                    SysBackendApiTable sysBackendApiTable = sysBackendApiTableService.selectByPrimaryKey(roleBackendApiTable.getBackendApiId());
                    apiUrls.add(sysBackendApiTable);
                }
            }

            AntPathMatcher antPathMatcher = new AntPathMatcher();
            //当前访问路径
            String requestURI = request.getRequestURI();
            //提交类型
            String urlMethod = request.getMethod();


            //判断当前路径中是否在资源鉴权中
            boolean rs = apiUrls.stream().anyMatch(item->{
                //判断URL是否匹配
                boolean hashAntPath = antPathMatcher.match(item.getBackendApiUrl(),requestURI);

                //判断请求方式是否和数据库中匹配（数据库存储：GET,POST,PUT,DELETE）
                String dbMethod = item.getBackendApiMethod();

                //处理null，万一数据库存值
                dbMethod = (dbMethod == null )? "": dbMethod;
                int hasMethod   = dbMethod.indexOf(urlMethod);

                //两者都成立，返回真，否则返回假
                return hashAntPath && (hasMethod !=-1);
            });
            //返回
            if (rs) {
                return rs;
            }else {
                throw  new MyaccessDeniedException("您没有访问该API的权限！");
            }

        }else{
            throw  new MyaccessDeniedException("不是UserDetails类型！");
        }

    }
}

























