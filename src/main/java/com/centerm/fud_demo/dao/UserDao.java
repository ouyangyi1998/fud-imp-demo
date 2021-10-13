package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 用户操作
 * @author ouyangyi
 */
@Component
public interface UserDao {
    /**
     * 通过username获取user
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 通过username获取用户角色
     * @param username 用户名
     * @return 用户角色
     */
    String findRoles(String username);

    /**
     * 创建用户
     * @param user 用户信息
     * @return 结果
     */
    Boolean createUser(User user);

    /**
     * 用户信息更新
     * @param user 用户信息
     * @return 结果
     */
    Boolean updateUser(User user);

    /**
     * 通过id查找用户
     * @param userId 用户id
     * @return 用户信息
     */
    User findById(int userId);

    /**
     * 修改用户头像
     * @param username 用户名
     * @param filePath 文件地址
     */
    void updateHeadPicture(String username,String filePath);

    /**
     * 找到之前的头像文件
     * @param username 用户名称
     * @return 用户头像url
     */
    String selectHeadPcitureURL(String username);


}
