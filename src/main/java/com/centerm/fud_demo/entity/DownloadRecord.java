package com.centerm.fud_demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 下载记录
 * @author jerry
 */
@Data
public class DownloadRecord {
    private Long id;
    private Timestamp createTime;
    private Long userId;
    private Long fileId;

    public DownloadRecord(Long userId, Long fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }
}
