package com.jiang.sys_rdbc.config.shiroOauth;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jiang.sys_rdbc.common.exception.RRException;
import com.jiang.sys_rdbc.entity.SysUserTokenEntity;
import com.jiang.sys_rdbc.entity.User;
import com.jiang.sys_rdbc.service.ShiroService;
import com.jiang.sys_rdbc.service.UserService;

/**
 * @author 蒋雨岳
 * @Date 2020/6/23 0023
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Resource
    private ShiroService shiroService;

    @Autowired
    UserService          userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("******************执行授权逻辑");

        User user = (User) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        //用户有多个角色  角色有多个菜单权限
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String accessToken = (String) token.getPrincipal(); //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken); //token失效

        if (tokenEntity == null || tokenEntity.getExpireTime().getTime()<System.currentTimeMillis()) {
            throw new RRException("token失效，请重新登录");
        }
        //查询用户信息
        User user = shiroService.queryUser(tokenEntity.getUserId());

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
