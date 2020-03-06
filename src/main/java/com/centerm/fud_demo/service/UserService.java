package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;

/**
 * 用户服务类
 * @author jerry
 */
public interface UserService {

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过用户名查找用户权限
     * @param username 用户名
     * @return 用户
     */
    String findRoles(String username);

    /**
     * 创建用户
     * @param user 用户实体类
     */
    void createUser(User user);

    /**
     * 修改用户密码
     * @param username 用户名
     * @param password 密码
     */
    void changePassword(String username,String password);

}