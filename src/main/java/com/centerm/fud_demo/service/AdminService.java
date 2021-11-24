package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 管理员服务
 * @author jerry
 */
public interface AdminService {

    /**
     * ban用户
     * @param userId 用户id
     * @return 结果
     */
    Boolean banUser(Long userId);

    /**
     * 释放用户
     * @param userId 用户id
     * @return 结果
     */
    Boolean releaseUser(Long userId);

    /**
     * 获取用户（除了管理员和超级管理员）
     * @param userId 用户id
     * @return 用户列表
     */
    List<User> getUserExceptAdminAndSuperVIP(Long userId);

    /**
     * 利用关键词搜索用户
     * @param contents 关键词
     * @return 用户列表
     */
    List<User> getUserLikeContents(String contents);

    /**
     * 给折线图带去上传文件信息
     * @return 文件上传信息
     */
    List<Map<String,Object>> getAllUploadToMorrisJs();

    /**
     * 给折线图带去下载文件信息
     * @return 文件上传信息
     */
    List<Map<String,Object>> getAllDownloadToMorrisJs();

    /**
     * 获取管理员人数
     * @return 管理员人数
     */
    Long getAdminNumber();

    /**
     * 获取用户总人数
     * @return 所有用户人数
     */
    Long getAllUserNumber();
}
