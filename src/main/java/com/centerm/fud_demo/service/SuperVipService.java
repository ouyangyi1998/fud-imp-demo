package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;

import java.util.List;


/**
 * 超级管理员服务
 * @author jerry
 */
public interface SuperVipService {
    /**
     * 成为管理员
     * @param userId 用户id
     * @return 结果
     */
    Boolean becomeAdmin(Long userId);

    /**
     * 移除管理员
     * @param userId 用户
     * @return 结果
     */
    Boolean removeAdmin(Long userId);

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色id
     */
    Long getUserRoles(Long userId);

    /**
     * 获取所有用户（除了超级管理员）
     * @return 角色信息
     */
    List<User> getAllUserExceptSuperVIP();

    /**
     * 移除用户
     * @param userId 用户id
     */
    void removeUser(Long userId);


}
