package com.centerm.fud_demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类　实现注册账户名重复异常获取
 * @author         Jerry
 * @date           20/01/20
 * @version        1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UsernameRepeatingException extends Exception {
    private String errorCode;
    private String errorMsg;

    public UsernameRepeatingException() {}

    public UsernameRepeatingException(String s, String errorCode, String errorMsg) {
        super(s);
        this.errorCode = errorCode;
        this.errorMsg  = errorMsg;
    }
}

