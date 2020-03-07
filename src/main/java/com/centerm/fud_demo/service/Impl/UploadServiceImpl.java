package com.centerm.fud_demo.service.Impl;

import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.dao.FileDao;
import com.centerm.fud_demo.entity.FileForm;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.service.UploadService;
import com.centerm.fud_demo.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/30 下午2:20
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Value("${filePath}")
    private String uploadPath;
    @Value("${backupPath}")
    private String backupPath;
    private Long userId = null;

    @Autowired
    FileDao fileDao;

    @Override
    public Long getUploadTimes() {
        return fileDao.getUploadTimes();
    }

    @Override
    public List<FileRecord> getLatestUploaded(Long userId) {
        return fileDao.getLatestUploaded(userId);
    }

    @Override
    public Long getUploadTimesByCurrUser(Long userId) {
        return fileDao.getUploadTimesByCurrUser(userId);
    }

    @Override
    public Map<String, Object> findByFileMd5(String md5, Long currUserId) {
        userId = currUserId;
        FileRecord uploadFile = fileDao.findFileByFileMd5(md5, userId);
        System.out.println(uploadFile==null);
        log.info("uploadFile: " + uploadFile);
        Map<String, Object> map = null;
        if (null == uploadFile){
            log.info("File is not uploaded...");
            map = new HashMap<>();
            map.put("flag", 0);
            map.put("uuid", FileUtil.genUniqueKey());
            map.put("date", sdf.format(new Date()));
        }else{
            //上传过该文件，判断文件还是否存在
            File file = new File(uploadPath + "real/" + userId + "/" + uploadFile.getUuid() + "/" + uploadFile.getName() + "." + uploadFile.getSuffix());
            System.out.println(file.exists());
            if (file.exists()){
                log.info("findByFileMd5: file already exists..." );
                if (uploadFile.getStatus() == 1){
                    System.out.println("1");
                    log.info("File is not complete...");
                    map = new HashMap<>();
                    map.put("flag", 1);
                    map.put("uuid", uploadFile.getUuid());
                    map.put("date", sdf.format(new Date()));
                }else if(uploadFile.getStatus() == 2){
                    System.out.println("2");
                    log.info("File is complete...");
                    map = new HashMap<>();
                    map.put("flag", 2);
                }
            }else {
                map = new HashMap<>();
                map.put("flag", 0);
                map.put("uuid", uploadFile.getUuid());
                map.put("date", sdf.format(new Date()));
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> realUpload(FileForm form, MultipartFile multipartFile, Long currUserId) throws Exception {
        userId = currUserId;
        String action = form.getAction();
        String uuid = form.getUuid();
        int index = Integer.valueOf(form.getIndex());
        String md5 = form.getMd5();
        int total = Integer.valueOf(form.getTotal());
        String fileName = form.getName();
        String size = form.getSize();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String tempDirectory = uploadPath + "temp/" + uuid;
        String saveDirectory = uploadPath + "real/"+ userId + "/" + uuid;
        String filePath = saveDirectory + "/" + fileName;
        //验证路径是否存在，不存在则创建目录
        File tempPath = new File(tempDirectory);
        if (!tempPath.exists()){
            tempPath.mkdirs();
        }
        File path = new File(saveDirectory);
        if (!path.exists()){
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(tempDirectory, uuid + "_" + index);
        //根据action执行操作
        Map<String, Object> map = null;
        if (Constants.UPLOAD.equals(action)){
            //分片上传中出错，有残余，时需要删除分片后重新上传
            if (file.exists()){
                file.delete();
            }
            multipartFile.transferTo(new File(tempDirectory, uuid + "_" + index));
            map = new HashMap<>();
            map.put("flag", "1");
            map.put("uuid", uuid);
        }
        if (path.isDirectory()){
            File[] fileArray = tempPath.listFiles();
            if (fileArray != null){
                if (fileArray.length == total){
                    //分块全部上传完毕，合并
                    log.info("开始合并...");
                    File newFile = new File(saveDirectory, fileName);
                    //文件追加写入
                    FileOutputStream outputStream = new FileOutputStream(newFile, true);
                    byte[] byt = new byte[10 * 1024 * 1024];
                    int len;
                    //分片文件
                    FileInputStream temp = null;
                    for (int i = 0; i < total; i++) {
                        int j = i + 1;
                        temp = new FileInputStream(new File(tempDirectory, uuid + "_" + j));
                        while ((len = temp.read(byt)) != -1){
                            outputStream.write(byt, 0, len);
                        }
                    }
                    temp.close();
                    outputStream.close();
                    log.info("删除分片信息...");
                    FileUtil.deleteDirectory(tempDirectory);
                    FileRecord uploadFile = new FileRecord();
                    if (1 == total){
                        uploadFile.setUuid(uuid);
                        uploadFile.setStatus(2);
                        uploadFile.setName(fileName.substring(0, fileName.lastIndexOf(".")));
                        uploadFile.setMd5(md5);
                        uploadFile.setSuffix(suffix);
                        uploadFile.setLocalUrl(filePath);
                        uploadFile.setSize(FileUtil.getFormatSize(Double.valueOf(size)));
                        uploadFile.setUserId(userId);
                        uploadFile.setBackupUrl(backupPath + md5);
                        fileDao.saveFileSmall(uploadFile);
                    }else
                    {
                        uploadFile.setUuid(uuid);
                        uploadFile.setStatus(2);
                        fileDao.saveFileEnd(uploadFile);
                    }
                    map = new HashMap<>();
                    map.put("uuid", uuid);
                    map.put("flag", 2);
                    return map;
                }else if (1 == index){
                    //第一个分片上传时记录到数据库
                    FileRecord uploadFile = new FileRecord();
                    uploadFile.setMd5(md5);
                    String name = fileName.substring(0, fileName.lastIndexOf("."));
                    uploadFile.setUuid(uuid);
                    uploadFile.setStatus(1);
                    uploadFile.setName(name);
                    uploadFile.setSuffix(suffix);
                    uploadFile.setLocalUrl(filePath);
                    uploadFile.setSize(FileUtil.getFormatSize(Double.valueOf(size)));
                    uploadFile.setUserId(userId);
                    uploadFile.setBackupUrl(backupPath + md5);
                    fileDao.saveFileBegin(uploadFile);
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> check(FileForm form){
        String uuid = form.getUuid();
        int index = Integer.valueOf(form.getIndex());
        String partMd5 = form.getPartMd5();
        int total = Integer.valueOf(form.getTotal());
        String tempDirectory = uploadPath + "temp/" + uuid;
        //验证路径是否存在，不存在则创建目录
        File path = new File(tempDirectory);
        if (!path.exists()){
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(tempDirectory, uuid + "_" + index);
        //根据action来执行不同的操作
        Map<String, Object> map = null;
        String md5Str = FileUtil.getFileMd5(file);
        if (md5Str != null && md5Str.length() == 31){
            log.info("check length = " + partMd5.length() + " md5Str length " + md5Str.length() + "    " + partMd5 + "  " + md5Str);
            md5Str = "0" + md5Str;
        }
        if (md5Str != null && md5Str.equals(partMd5)){
            //已上传该分片
            map = new HashMap<>();
            map.put("flag", "1");
            map.put("uuid", uuid);
            if (index != total){
                return map;}
            }else{
            //分片未上传
            map = new HashMap<>();
            map.put("flag", "0");
            map.put("uuid", uuid);
            return map;
        }
        return map;
    }

}
