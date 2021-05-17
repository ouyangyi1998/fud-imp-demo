package com.centerm.fud_demo.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息更新实体类
 * @author ouyangyi
 */
@Data
public class UpdateForm {
    private MultipartFile file;
    private String password;
}
