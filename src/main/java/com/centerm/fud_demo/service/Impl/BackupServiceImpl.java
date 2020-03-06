package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.service.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.List;

/**
 * 备份文件操作实现类
 * @author sheva
 */
@Service
@Slf4j
public class BackupServiceImpl implements BackupService {
    @Autowired
    private FileDao fileDao;



    @Override
    public void backupFile(String copyFrom, String copyTo, String fileName) {
        log.info("backup start...");
        long start = System.currentTimeMillis();
        File source = new File(copyFrom);
        File target = new File(copyTo + File.separator + fileName);
        File targetFolder = new File(copyTo);
        FileInputStream in = null;
        FileOutputStream out = null;
        if (!source.exists() || !source.isFile()){
            log.error("Source doesn't exists or source isn't a file...");
        }
        if (targetFolder.exists()){
            log.info("File is already backup...");
            return;
        }
        targetFolder.mkdirs();
        try{
            target.createNewFile();
            in = new FileInputStream(source);
            out = new FileOutputStream(target);
            FileChannel inChannel = in.getChannel();
            WritableByteChannel outChannel = out.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
            in.close();
            out.close();
        }catch (FileNotFoundException e){
            log.error("File not found...");
        }catch (IOException e){
            log.error(e.getMessage());
        }
        log.info("Backup Finished...");
        long end = System.currentTimeMillis();
        log.info("Backup lasts: " + (end - start) + "ms");
    }

    @Override
    public String getFileBackupPath(String filePath) {
        return fileDao.getFileBackupUrl(filePath);
    }

}
