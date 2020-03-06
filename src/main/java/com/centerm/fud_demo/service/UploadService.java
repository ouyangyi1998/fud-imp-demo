package com.centerm.fud_demo.service;

import com.centerm.fud_demo.entity.FileForm;
import com.centerm.fud_demo.entity.FileRecord;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/1/30 下午2:17
 */
public interface UploadService {


    /**
     * 获取总上传次数
     * @return
     */
    Long getUploadTimes();


    /**
     * 获取最新上传文件的前五个
     * @param userId 用户id
     * @return
     */
    List<FileRecord> getLatestUploaded(Long userId);

    /**
     * 获取用户的上传次数
     * @param userId
     * @return
     */
    Long getUploadTimesByCurrUser(Long userId);

    /**
     * 根据用户id与需要上传的文件md5查找文件
     * @param md5
     * @param userId
     * @return
     */
    Map<String, Object> findByFileMd5(String md5, Long userId);

    /**
     * 上传文件
     * @param form 文件表单信息
     * @param multipartFile 文件
     * @throws Exception
     */
    Map<String, Object> realUpload(FileForm form, MultipartFile multipartFile, Long userId) throws Exception;

    /**
     * 确定文件是否存在
     * @param form 文件信息
     * @throws Exception
     */
    Map<String, Object> check(FileForm form) throws Exception;
}
