package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.SuperVipDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.SuperVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 超级管理员实现类
 * @author sheva
 */
@Service
public class SuperVipServiceImpl implements SuperVipService {

    @Autowired
    private SuperVipDao superVipDao;

    /**
     * 把普通用户授权为管理员
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public Boolean becomeAdmin(Long userId) {
        return superVipDao.becomeAdmin(userId);
    }

    /**
     * 把管理员用户降级为普通用户
     * @param userId 用户
     * @return 结果
     */
    @Override
    public Boolean removeAdmin(Long userId) {
        return superVipDao.removeAdmin(userId);
    }

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色id
     */
    @Override
    public Long getUserRoles(Long userId) {
        return superVipDao.getUserRoles(userId);
    }

    /**
     * 获取除了超级管理员之外的其他用户
     * @return 用户列表
     */
    @Override
    public List<User> getAllUserExceptSuperVIP() {
        return superVipDao.getAllUserExceptSuperVIP();
    }

    /**
     * 移除用户
     * @param userId 用户id
     */
    @Override
    public void removeUser(Long userId) {
        superVipDao.removeUser(userId);
    }

}
