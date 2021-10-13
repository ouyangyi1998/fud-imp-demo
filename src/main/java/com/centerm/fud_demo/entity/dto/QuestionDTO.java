package com.centerm.fud_demo.entity.dto;

import com.centerm.fud_demo.entity.User;
import lombok.Data;

/**
 * 帖子内容
 * @author ouyangyi
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer createId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private Long createTime;
    private User user;

}
