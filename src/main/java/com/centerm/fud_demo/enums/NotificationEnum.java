package com.centerm.fud_demo.enums;

/**
 * 用户提醒类型
 * @author ouyangyi
 */

public enum NotificationEnum {
    /**
     * 其他用户回复了评论
     */
    NOTIFICATION_COMMENT(1,"回复了评论"),
    /**
     * 其他用户回复了问题
     */
    NOTIFICATION_QUESTION(2,"回复了问题");

    private int type;
    private String name;

    NotificationEnum(int type, String name){
        this.type=type;
        this.name=name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
