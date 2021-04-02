package com.centerm.fud_demo.entity.dto;

import lombok.Data;

/**
 * @author ouyangyi
 */
@Data
public class CommentCreateDTO {
    private Integer parentId;
    private Integer type;
    private String content;
}
