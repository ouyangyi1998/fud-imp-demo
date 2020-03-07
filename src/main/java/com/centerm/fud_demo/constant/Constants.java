package com.centerm.fud_demo.constant;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/11 下午1:25
 */
public class Constants {

    /**
     * 用户状态
     * 0 正常
     * 1 封禁
     */
    public static final Integer NORMAL = 0;
    public static final Integer BAN = 1;
    /**
     * 操作状态
     * 0 失败
     * 1 成功
     */
    public static final Integer FAIL = 0;
    public static final Integer SUCCESS = 1;
    /**
     * 用户身份
     * 1 普通用户
     * 2 管理员
     */
    public static final long USER = 1;
    public static final long ADMIN = 2;
    /**
     * 接受协议条款
     */
    public static final String ACCEPT = "1";
    /**
     * 超级管理员id 1
     */
    public static final Long SUPERVIPID = 1L;
    /**
     * 文件上传与检查的字符标识
     */
    public static final String UPLOAD = "upload";
    public static final String CHECK = "check";

}
