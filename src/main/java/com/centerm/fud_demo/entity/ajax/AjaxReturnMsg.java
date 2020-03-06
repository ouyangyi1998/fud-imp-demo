package com.centerm.fud_demo.entity.ajax;

import lombok.Data;

@Data
public class AjaxReturnMsg {
    private Integer flag;
    private String username;
    private String msg;

    public AjaxReturnMsg(Integer flag, String username, String msg) {
        this.flag = flag;
        this.username = username;
        this.msg = msg;
    }

    public AjaxReturnMsg() {
    }
}
