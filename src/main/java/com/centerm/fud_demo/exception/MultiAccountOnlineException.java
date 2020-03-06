package com.centerm.fud_demo.exception;

import lombok.Data;

/**
 *  多账号在线异常处理
 *
 * @author         Jerry
 * @date           20/01/20
 * @version        1.0
 */
@Data
public class MultiAccountOnlineException extends Exception {
    private String errorCode;
    private String errorMsg;

    public MultiAccountOnlineException() {}

    public MultiAccountOnlineException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg  = errorMsg;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
