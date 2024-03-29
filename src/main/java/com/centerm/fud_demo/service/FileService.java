package com.centerm.fud_demo.service;

import java.util.List;
import java.util.Map;

import com.centerm.fud_demo.entity.FileRecord;

/**
 * File相关操作接口
 * @author sheva
 */
public interface FileService {


    /**
     * 根据文件id删除文件（管理员操作）
     * @param fileId 文件id
     * @return 结果
     */
    Boolean deleteFile(Long fileId);

    /**
     * 根据用户id和文件id删除文件
     * @param userId 用户id
     * @param fileId 文件id
     * @return 结果
     */
    Boolean deleteFileById(Long userId, Long fileId);

    /**
     * 更新文件（downloadTimes加1）
     * @param fileId 文件id
     * @return 结果
     */
    Boolean updateFile(Long fileId);

    /**
     * 获取所有文件
     * @return 文件信息
     */
    List<FileRecord> getAllFile();
    /**
     * 给折线图带去用户下载信息
     * @param userId 用户id
     * @return 折线图
     */
    List<Map<String, Object>> getDownloadToMorrisJs(Long userId);

    /**
     * 根据用户id获取文件
     * @param userId 用户id
     * @return 文件信息
     */
    List<FileRecord> getFileByUserId(Long userId);

    /**
     * 通过关键词搜索该用户文件
     *  @param contents 关键词
     *  @param userId 用户id
     * @return 文件信息
     */
    List<FileRecord> getFileLikeContents(String contents, Long userId);

    /**
     * 给折线图带去用户上传信息
     * @param userId 用户id
     * @return 折线图
     */
    List<Map<String, Object>> getUploadToMorrisJs(Long userId);

    /**
     * 获取用户上传的文件数目
     * @return 文件数目
     */
    Integer getFileNumberById(Long userId);

    /**
     * 获取热门文件
     * @return 文件信息
     */
    List<FileRecord> getHotFile();

    /**
     * 把文件范围修改到public
     * @param fileId 文件id
     */
    void changeFileScopeToPublic(Long fileId);
    /**
     * 把文件范围修改到private
     * @param fileId 文件id
     */
    void changeFileScopeToPrivate(Long fileId);

}

