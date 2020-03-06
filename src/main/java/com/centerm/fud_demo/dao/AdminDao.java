package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 管理员映射
 * @author jerry
 */
@Component
public interface AdminDao {


    /**
     * 获取用户状态
     * @param userId
     * @return
     */
    int getUserState(Long userId);

    /**
     * ban用户
     * @param userId 用户id
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
     * 给折线图传送上传信息
     * @return
     */
    List<Map<String,Object>> getAllUploadToMorrisJs();

    /**
     * 给折线图传送下载信息
     * @return
     */
    List<Map<String,Object>> getAllDownloadToMorrisJs();

    /**
     * 获取管理员数目
     * @return
     */
    Long getAdminNumber();

    /**
     * 获取用户数目
     * @return
     */
    Long getAllUserNumber();
}
