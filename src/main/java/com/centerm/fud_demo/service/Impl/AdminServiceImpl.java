package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.AdminDao;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


/**
 * 管路员服务实现类
 * @author jerry
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 管理员封禁用户
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public Boolean banUser(Long userId) {
        return adminDao.banUser(userId);
    }

    /**
     * 管理员解封用户
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public Boolean releaseUser(Long userId) {
        return adminDao.releaseUser(userId);
    }

    /**
     * 获取除了管理员和超级管理员的其他用户
     * @param userId 用户id
     * @return 用户列表
     */
    @Override
    public List<User> getUserExceptAdminAndSuperVIP(Long userId) {
        return adminDao.getUserExceptAdminAndSuperVIP(userId);
    }

    /**
     * 根据关键词搜索用户
     * @param contents 关键词
     * @return 用户列表
     */
    @Override
    public List<User> getUserLikeContents(String contents) {
        return adminDao.getUserLikeContents("%"+contents+"%");
    }

    /**
     * 管理员文件上传折线图
     * @return 折线图
     */
    @Override
    public List<Map<String, Object>> getAllUploadToMorrisJs() {
        return adminDao.getAllUploadToMorrisJs();
    }

    /**
     * 管理员文件下载折线图
     * @return 折线图
     */
    @Override
    public List<Map<String, Object>> getAllDownloadToMorrisJs() {
        return adminDao.getAllDownloadToMorrisJs();
    }

    /**
     * 获取到管理员人数
     * @return 管理员人数
     */
    @Override
    public Long getAdminNumber() {
        return adminDao.getAdminNumber();
    }

    /**
     * 获取所有用户人数
     * @return 用户人数
     */
    @Override
    public Long getAllUserNumber() {
        return adminDao.getAllUserNumber();
    }
}
