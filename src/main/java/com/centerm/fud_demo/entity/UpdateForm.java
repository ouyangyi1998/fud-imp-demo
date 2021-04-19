package com.centerm.fud_demo.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ouyangyi
 */
@Data
public class UpdateForm {
    private MultipartFile file;
    private String password;
}
