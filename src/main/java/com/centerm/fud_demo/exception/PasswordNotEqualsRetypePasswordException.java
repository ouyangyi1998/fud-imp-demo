package com.centerm.fud_demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码和重复密码不同
 * @author ouyangyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PasswordNotEqualsRetypePasswordException extends Exception {
    private String errorCode;
    private String errorMsg;

    public PasswordNotEqualsRetypePasswordException() {
        super();
    }
}
