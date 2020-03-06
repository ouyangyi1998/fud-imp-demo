package com.centerm.fud_demo.shiro;

import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jerry
 */
@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("Current User is：　" + user.getUsername());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String roleName = userService.findRoles(user.getUsername());
        Set<String> set=new HashSet<>();
        set.add(roleName);
        authorizationInfo.setRoles(set);
        log.info("Current permission is： " + roleName);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
        String username=(String)token.getPrincipal();
        User user = null;
        try {
            user = userService.findByUsername(username);
        }catch (NullPointerException e) {}
        if(null == user)
        {
            throw new UnknownAccountException();
        }
        if (user.getState().equals(Constants.BAN))
        {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getUsername()),getName());
        return authenticationInfo;
    }
    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}