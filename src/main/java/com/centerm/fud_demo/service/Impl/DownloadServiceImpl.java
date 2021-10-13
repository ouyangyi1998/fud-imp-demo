package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:35
 */
@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    FileDao fileDao;

    /**
     * 添加下载记录
     * @param downloadRecord 下载记录
     * @return 结果
     */
    @Override
    public Boolean addDownloadRecord(DownloadRecord downloadRecord) {
        return fileDao.addDownloadRecord(downloadRecord);
    }

    /**
     * 获取到最多下载的文件记录
     * @param userId 用户id
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getMostDownloadRecordById(Long userId) {
        return fileDao.getMostDownloadRecordById(userId);
    }

    /**
     * 获取下载次数
     * @param userId 用户id
     * @return 下载次数
     */
    @Override
    public Long getDownloadTimesByUserId(Long userId) {
        return fileDao.getDownloadTimesByUserId(userId);
    }

    /**
     * 获取总下载次数
     * @return 总下载次数
     */
    @Override
    public Long getDownloadTimes() {
        return fileDao.getDownloadTimes();
    }

    /**
     * 删除下载记录
     * @param fileId 文件id
     * @return 结果
     */
    @Override
    public Boolean deleteDownloadRecord(Long fileId) {
        return fileDao.deleteDownloadRecord(fileId);
    }

    /**
     * 获取最新的下载情况
     * @param userId 用户id
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getLatestDownloaded(Long userId) {
        return fileDao.getLatestDownloaded(userId);
    }

    /**
     * 文件下载
     * @param id　　文件id
     * @param response　响应
     * @throws Exception 异常
     */
    @Override
    public void downloadFile(Long id, HttpServletResponse response) throws Exception{
        FileRecord downloadFile = fileDao.getFileById(id);
        File file = new File(downloadFile.getLocalUrl());
        if (!file.exists()){
            log.error("File didn't exist...");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" +new String( file.getName().getBytes("gb2312"), "ISO8859-1" ));

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
            log.info("Start downloading: " + downloadFile.getName());
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while((i = bis.read(buff)) != -1){
                os.write(buff, 0, i);
                os.flush();
            }

            log.info("Download successfully...");
        }catch (IOException e){
            log.warn("Current user cancelled download...");
        }
    }

    /**
     * 获取到最多的下载记录
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getMostDownloadRecord() {
        return fileDao.getMostDownloadRecord();
    }
}
