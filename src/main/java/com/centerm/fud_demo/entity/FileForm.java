package com.centerm.fud_demo.entity;

import lombok.Data;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/25 上午10:30
 */
@Data
public class FileForm {

    private String md5;
    private String uuid;
    private String date;
    private String name;
    private String size;
    private String total;
    private String index;
    private String action;
    private String partMd5;

}
