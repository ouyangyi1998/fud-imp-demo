package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserDao {
    /**
     * 通过username获取user
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过username获取用户角色
     * @param username
     * @return
     */
    String findRoles(String username);

    /**
     * 创建用户
     * @param user
     */
    Boolean createUser(User user);

    /**
     * 用户信息更新
     * @param user
     */
    Boolean updateUser(User user);
}