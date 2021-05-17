package com.centerm.fud_demo.entity;

import lombok.Data;

/**
 * 用户帖子实体
 * @author ouyangyi
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Integer createId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private Long createTime;
}
