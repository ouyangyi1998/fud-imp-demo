package com.centerm.fud_demo.entity;

import lombok.Data;

/**
 * 下载记录实体
 * @author jerry
 */
@Data
public class DownloadRecord {
    private Long id;
    private String createTime;
    private Long userId;
    private Long fileId;

    public DownloadRecord(Long userId, Long fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }
}
