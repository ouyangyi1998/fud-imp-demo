package com.centerm.fud_demo.enums;

import lombok.Data;

/**
 * @author ouyangyi
 */
public enum FileTypeEnum {
    AUDIO(0,"音频"),
    VEDIO(1,"视频"),
    DOCUMENT(2,"文档"),
    PICTURE(3,"图片"),
    OTHER(4,"其他")
    ;

    private int type;
    private String name;

    FileTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
