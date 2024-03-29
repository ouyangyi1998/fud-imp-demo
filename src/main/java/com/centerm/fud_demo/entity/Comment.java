package com.centerm.fud_demo.entity;

import lombok.Data;

/**
 * 用户帖子实体
 * @author ouyangyi
 */
@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentor;
    private Integer likeCount;
    private Long createTime;
    private Integer commentCount;
    private String content;
}
