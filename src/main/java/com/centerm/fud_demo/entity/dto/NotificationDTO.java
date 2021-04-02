package com.centerm.fud_demo.entity.dto;

import com.centerm.fud_demo.entity.User;
import lombok.Data;

/**
 * @author ouyangyi
 */
@Data
public class NotificationDTO {
    private Integer id;
    private Integer receiver;
    private Integer type;
    private Long createTime;
    private Integer status;
    private User notifier;
    private String outerContent;
    private Integer questionId;
}
