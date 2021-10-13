package com.centerm.fud_demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 不同意license异常
 * @author ouyangyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NotAcceptTermsException extends Exception {
    private String errorCode;
    private String errorMsg;

    public NotAcceptTermsException(String errorCode) {
        this.errorCode = errorCode;
    }

    public NotAcceptTermsException() {
    }
}
