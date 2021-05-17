package com.centerm.fud_demo.entity.dto;

import com.centerm.fud_demo.entity.User;
import lombok.Data;

/**
 * 用户回复
 * @author ouyangyi
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentor;
    private Integer likeCount;
    private Long createTime;
    private Integer commentCount;
    private String content;
    private User user;
    private String commentorName;

}
