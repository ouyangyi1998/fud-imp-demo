package com.centerm.fud_demo.utils;

import com.centerm.fud_demo.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 用户密码操作工具类
 * @author ouyangyi
 */
public class PasswordHelper {
    /**
     * 定义算法 MD5
     */
    private static final String ALGORITHM_NAME = "MD5";
    private final static int HASH_ITERATIONS = 5;

    /**
     * 加密用户密码
     * @param user 用户
     */
    public static void encryptPassword(User user)
    {
        String newPassword = new SimpleHash(ALGORITHM_NAME,user.getPassword(), ByteSource.Util.bytes(user.getUsername()),HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }
}
