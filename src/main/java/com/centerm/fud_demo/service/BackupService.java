package com.centerm.fud_demo.service;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/4 上午10:31
 */
public interface BackupService {

    /**
     * 备份文件
     * @param copyFrom 原地址
     * @param copyTo 目标地址
     * @param fileName 文件名
     */
    void backupFile(String copyFrom, String copyTo, String fileName);

    /**
     * 获取文件的备份地址
     * @param filePath 文件地址
     * @return
     */
    String getFileBackupPath(String filePath);

}
