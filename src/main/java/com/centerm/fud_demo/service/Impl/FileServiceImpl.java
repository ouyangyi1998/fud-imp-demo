package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


/**
 * 文件相关操作实现类
 * @author sheva
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    /**
     * 通过用户id获取到文件信息
     * @param userId 用户id
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getFileByUserId(Long userId) {
        return fileDao.getFileByUserId(userId);
    }

    /**
     * 通过id删除文件
     * @param userId 用户id
     * @param fileId 文件id
     * @return 结果
     */
    @Override
    public Boolean deleteFileById(Long userId, Long fileId) {
        FileRecord deleteFile = fileDao.getFileById(fileId);
        FileUtil.deleteLocalFile(deleteFile.getLocalUrl());
        return fileDao.deleteFileById(userId, fileId);
    }

    /**
     * 删除文件
     * @param fileId 文件id
     * @return 结果
     */
    @Override
    public Boolean deleteFile(Long fileId) {
        FileRecord deleteFile = fileDao.getFileById(fileId);
        FileUtil.deleteLocalFile(deleteFile.getLocalUrl());
        return fileDao.deleteFile(fileId);
    }

    /**
     * 获取到所有的文件
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getAllFile() {
        return fileDao.getAllFile();
    }

    /**
     * 更新文件
     * @param fileId 文件id
     * @return 结果
     */
    @Override
    public Boolean updateFile(Long fileId) {
        return fileDao.updateFile(fileId);
    }

    /**
     * 通过关键词获取到文件
     * @param contents 关键词
     * @param userId 用户id
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getFileLikeContents(String contents,Long userId) {
        return fileDao.getFileLikeContents("%" + contents + "%", userId);
    }

    /**
     * 文件上传折线图
     * @param userId 用户id
     * @return 折线图
     */
    @Override
    public List<Map<String,Object>> getUploadToMorrisJs(Long userId) {
        return fileDao.getUploadToMorrisJs(userId);
    }

    /**
     * 文件下载折线图
     * @param userId 用户id
     * @return 折线图
     */
    @Override
    public List<Map<String, Object>> getDownloadToMorrisJs(Long userId) {
        return fileDao.getDownloadToMorrisJs(userId);
    }

    /**
     * 通过用户id获取文件数目
     * @param userId 用户id
     * @return 文件数目
     */
    @Override
    public Integer getFileNumberById(Long userId) {
        return fileDao.getFileNumberById(userId);
    }

    /**
     * 获取热门
     * @return 文件信息
     */
    @Override
    public List<FileRecord> getHotFile() {
        return fileDao.getHotFile();
    }

    /**
     * 把文件范围转为公有
     * @param fileId 文件id
     */
    @Override
    public void changeFileScopeToPublic(Long fileId) {
        fileDao.changeFileScopeToPublic(fileId);
    }

    /**
     * 把文件范围转为私有
     * @param fileId 文件id
     */
    @Override
    public void changeFileScopeToPrivate(Long fileId) {
        fileDao.changeFileScopeToPrivate(fileId);
    }
}
