package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;

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
     * @param id 文件id
     * @param response 响应
     * @throws Exception 异常
     */
    void downloadFile(Long id, HttpServletResponse response) throws Exception;

    /**
     * 添加下载记录
     * @param downloadRecord 下载记录
     * @return 结果
     */
    Boolean addDownloadRecord(DownloadRecord downloadRecord);

    /**
     * 获取该用户文件下载次数最多的前5个
     * @return 文件下载记录
     */
    List<FileRecord> getMostDownloadRecordById(Long userId);

    /**
     * 获取所有文件下载次数最多的前5个
     * @return 下载记录
     */
    List<FileRecord> getMostDownloadRecord();

    /**
     * 根据用户id获取总下载次数
     * @param userId 用户id
     * @return
     */
    Long getDownloadTimesByUserId(Long userId);

    /**
     * 获取所有人的下载总数
     * @return 下载数
     */
    Long getDownloadTimes();

    /**
     * 删除下载记录
     * @param fileId 文件id
     * @return 结果
     */
    Boolean deleteDownloadRecord(Long fileId);

    /**
     * 获取最新下载文件的前五个
     * @param userId 用户id
     * @return 下载记录
     */
    List<FileRecord> getLatestDownloaded(Long userId);
}
