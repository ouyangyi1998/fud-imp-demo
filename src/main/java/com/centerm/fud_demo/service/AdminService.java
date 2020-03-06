package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 管理员服务
 * @author jerry
 */
public interface AdminService {

    /**
     * ban用户
     * @param userId
     * @return
     */
    Boolean banUser(Long userId);

    /**
     * 释放用户
     * @param userId 用户id
     * @return
     */
    Boolean releaseUser(Long userId);

    /**
     * 获取用户（除了管理员和超级管理员）
     * @param userId 用户id
     * @return
     */
    List<User> getUserExceptAdminAndSuperVIP(Long userId);

    /**
     * 利用关键词搜索用户
     * @param contents 关键词
     * @return
     */
    List<User> getUserLikeContents(String contents);

    /**
     * 给折线图带去上传文件信息
     * @return
     */
    List<Map<String,Object>> getAllUploadToMorrisJs();

    /**
     * 给折线图带去下载文件信息
     * @return
     */
    List<Map<String,Object>> getAllDownloadToMorrisJs();

    /**
     * 获取管理员人数
     * @return
     */
    Long getAdminNumber();

    /**
     * 获取用户总人数
     * @return
     */
    Long getAllUserNumber();
}
