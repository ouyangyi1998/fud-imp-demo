package com.centerm.fud_demo.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ouyangyi
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
