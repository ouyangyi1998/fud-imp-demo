package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.UserService;
import com.centerm.fud_demo.utils.PasswordHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户操作实现类
 * @author jerry
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过用户名找寻用户
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     *
     * @param username 用户名
     * @return
     */
    @Override
    public String findRoles(String username) {
        return userDao.findRoles(username);
    }

    /**
     *
     * @param user 用户实体类
     */
    @Override
    public void createUser(User user) {
        PasswordHelper.encryptPassword(user);
        userDao.createUser(user);
    }

    /**
     * 修改用户密码
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public void changePassword(String username, String password) {
        User user = userDao.findByUsername(username);
        user.setPassword(password);
        PasswordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    /**
     * 更新用户头像
     * @param username 用户名
     * @param filePath 密码
     */
    @Override
    public void updateHeadPicture(String username,String filePath) {
        userDao.updateHeadPicture(username,filePath);
    }

    /**
     * 找寻头像url
     * @param username 用户名称
     * @return url
     */
    @Override
    public String selectHeadPcitureURL(String username) {
        return userDao.selectHeadPcitureURL(username);
    }
}
