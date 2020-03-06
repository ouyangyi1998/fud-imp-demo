package com.centerm.fud_demo.exception;

public class PasswordNotEqualsRetypePasswordException extends Exception {
    private String errorCode;
    private String errorMsg;

    public PasswordNotEqualsRetypePasswordException() {
        super();
    }
}
