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

    @Override
    public List<FileRecord> getFileByUserId(Long userId) {
        return fileDao.getFileByUserId(userId);
    }

    @Override
    public Boolean deleteFileById(Long userId, Long fileId) {
        FileRecord deleteFile = fileDao.getFileById(fileId);
        FileUtil.deleteLocalFile(deleteFile.getLocalUrl());
        return fileDao.deleteFileById(userId, fileId);
    }

    @Override
    public Boolean deleteFile(Long fileId) {
        FileRecord deleteFile = fileDao.getFileById(fileId);
        FileUtil.deleteLocalFile(deleteFile.getLocalUrl());
        return fileDao.deleteFile(fileId);
    }

    @Override
    public List<FileRecord> getAllFile() {
        return fileDao.getAllFile();
    }

    @Override
    public Boolean updateFile(Long fileId) {
        return fileDao.updateFile(fileId);
    }


    @Override
    public List<FileRecord> getFileLikeContents(String contents,Long userId) {
        return fileDao.getFileLikeContents("%" + contents + "%", userId);
    }

    @Override
    public List<Map<String,Object>> getUploadToMorrisJs(Long userId) {
        return fileDao.getUploadToMorrisJs(userId);
    }

    @Override
    public List<Map<String, Object>> getDownloadToMorrisJs(Long userId) {
        return fileDao.getDownloadToMorrisJs(userId);
    }

}
