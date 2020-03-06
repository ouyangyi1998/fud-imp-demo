package com.centerm.fud_demo.exception;

import lombok.Data;

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
