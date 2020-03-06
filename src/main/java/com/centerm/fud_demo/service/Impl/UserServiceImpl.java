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

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);

    }

    @Override
    public String findRoles(String username) {
        return userDao.findRoles(username);
    }


    @Override
    public void createUser(User user) {
        PasswordHelper.encryptPassword(user);
        userDao.createUser(user);
    }

    @Override
    public void changePassword(String username, String password) {
        User user = userDao.findByUsername(username);
        user.setPassword(password);
        PasswordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

}