package com.centerm.fud_demo.exception;

import lombok.Data;

/**
 * 管理员对于账号封禁或解锁异常
 *
 * @author         Jerry
 * @date           20/01/20
 * @version       1.0
 */
@Data
public class AccountBanException extends Exception {
    private String errorCode;
    private String errorMsg;

    public AccountBanException() {}

    public AccountBanException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg  = errorMsg;
    }
}

