package com.centerm.fud_demo.enums;

/**
 * @author ouyangyi
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);
    private int status;
    NotificationStatusEnum(int status){
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
