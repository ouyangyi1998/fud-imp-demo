package com.centerm.fud_demo.enums;

/**
 * 用户提醒类型
 * @author ouyangyi
 */
public enum NotificationStatusEnum {
    /**
     * 未阅读
     */
    UNREAD(0),
    /**
     * 已阅读
     */
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
