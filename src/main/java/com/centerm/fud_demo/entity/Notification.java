package com.centerm.fud_demo.entity;

import lombok.Data;

/**
 * 用户提醒实体
 * @author ouyangyi
 */
@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Long createTime;
    private Integer status;
}
