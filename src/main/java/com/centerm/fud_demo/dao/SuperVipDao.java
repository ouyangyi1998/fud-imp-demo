package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 超级管理员映射
 * @author jerry
 */
@Component
public interface SuperVipDao {

    /**
     * 成为管理员
     * @param userId 用户id
     * @return 操作结果
     */
    Boolean becomeAdmin(Long userId);

    /**
     * 移除管理员
     * @param userId 用户id
     * @return 操作结果
     */
    Boolean removeAdmin(Long userId);

    /**
     * 获取用户角色
     * @param userId  用户id
     * @return 角色id
     */
    Long getUserRoles(Long userId);

    /**
     * 获取所有用户（除了管理员）
     * @return 用户列表
     */
    List<User> getAllUserExceptSuperVIP();

    /**
     * 移除用户
     * @param userId 用户id
     */
    void removeUser(Long userId);

}
