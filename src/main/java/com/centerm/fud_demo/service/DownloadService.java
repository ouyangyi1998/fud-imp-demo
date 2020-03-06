package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:34
 */
public interface DownloadService {
    /**
     * 下载文件
     * @param id　　文件id
     * @param response　　
     */
    void downloadFile(Long id, HttpServletResponse response);

    /**
     * 添加下载记录
     * @param downloadRecord
     * @return
     */
    Boolean addDownloadRecord(DownloadRecord downloadRecord);

    /**
     * 获取该用户文件下载次数最多的前5个
     * @return
     */
    List<FileRecord> getMostDownloadRecordById(Long userId);

    /**
     * 获取所有文件下载次数最多的前5个
     * @return
     */
    List<FileRecord>getMostDownloadRecord();

    /**
     * 根据用户id获取总下载次数
     * @param userId
     * @return
     */
    Long getDownloadTimesByUserId(Long userId);

    /**
     * 获取所有人的下载总数
     * @return
     */
    Long getDownloadTimes();

    /**
     * 删除下载记录
     * @param fileId 文件id
     * @return
     */
    Boolean deleteDownloadRecord(Long fileId);

    /**
     * 获取最新下载文件的前五个
     * @param userId 用户id
     * @return
     */
    List<FileRecord> getLatestDownloaded(Long userId);
}
