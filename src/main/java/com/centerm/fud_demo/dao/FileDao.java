package com.centerm.fud_demo.dao;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.FileRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * File相关操作映射
 * @author sheva
 */
@Component
public interface FileDao {

   /**
    * 根据md5查询文件
    * @param fileMd5 文件md5值
    * @param userId 用户id
    * @return 文件信息
    */
    FileRecord findFileByFileMd5(String fileMd5, Long userId) ;


    /**
    * 根据用户id获取上传文件
    * @param userId 用户id
    * @return 文件列表
    */
   List<FileRecord> getFileByUserId(Long userId);

   /**
    * 上传第一个分片时的文件存储
    * @param fileRecord 文件信息
    * @return 结果
    */
   Boolean saveFileBegin(FileRecord fileRecord);
   /**
    * 添加文件
    * @param fileRecord 文件实体
    * @return 文件
    */
   Boolean saveFileSmall(FileRecord fileRecord);

   /**
    * 上传文成后的文件更新
    * @param fileRecord 文件信息
    * @return 结果
    */
   Boolean saveFileEnd(FileRecord fileRecord);

   /**
    * 根据id获取文件
    * @param id  文件id
    * @return 文件信息
    */
   FileRecord getFileById(Long id);

   /**
    * 通过文件名称获取文件信息
    * @param fileName 文件名称
    * @return 文件信息
    */
   FileRecord getFileByName(String fileName);

   /**
    * 获取所有文件
    * @return 文件列表
    */
   List<FileRecord> getAllFile();
   /**
    * 添加下载记录
    * @param downloadRecord 下载记录消息
    * @return 结果
    */
   Boolean addDownloadRecord(DownloadRecord downloadRecord);

   /**
    * 获取用户最热门下载
    * @param userId 用户id
    * @return fileRecord集合
    */
   List<FileRecord> getMostDownloadRecordById(Long userId);
   /**
    * 获取最热门下载
    * @return fileRecord集合
    */
    List<FileRecord> getMostDownloadRecord();
   /**
    * 获取某个用户上传的文件的总下载次数
    * @param userId 用户id
    * @return 总下载次数
    */
   Long getDownloadTimesByUserId(Long userId);

   /**
    * 获取下载次数
    * @return 下载次数
    */
   Long getDownloadTimes();

   /**
    * 获取上传次数
    * @return 上传次数
    */
   Long getUploadTimes();

   /**
    * 根据用户id与文件id删除文件
    * @param userId 用户id
    * @param fileId 文件id
    * @return 结果
    */
   Boolean deleteFileById(Long userId, Long fileId);

   /**
    * 根据文件id删除文件（管理员操作）
    * @param fileId 文件id
    * @return 结果
    */
   Boolean deleteFile(Long fileId);

   /**
    * 根据文件名删除文件
    * @param filePath 文件路径
    */
   void deleteFileByPath(String filePath);

   /**
    * 更新文件信息（下载次数）
    * @param fileId 文件id
    * @return 结果
    */
   Boolean updateFile(Long fileId);

   /**
    * 更新文件记录
    * @param fileId 文件id
    * @param fileSize  文件大小
    * @param fileMd5 文件md5
    * @param backupPath 文件备份地址
    * @return 结果
    */
   Boolean updateFileRecord(Long fileId, String fileSize, String fileMd5, String backupPath);

   /**
    * 删除下载信息
    * @param fileId 文件id
    * @return 结果
    */
   Boolean deleteDownloadRecord(Long fileId);

   /**
    * 获取最新上传的前五个文件
    * @param userId 用户id
    * @return 文件集合
    */
   List<FileRecord> getLatestUploaded(Long userId);

   /**
    * 获取最新下载的前五个文件
    * @param userId 用户id
    * @return 文件集合
    */
   List<FileRecord> getLatestDownloaded(Long userId);

   /**
    * 搜索文件
    * @param contents 关键词
    * @param userId 用户id
    * @return 文件列表
    */
   List<FileRecord> getFileLikeContents(String contents,Long userId);

   /**
    * 给折线图传送用户上传信息
    * @param userId 用户id
    * @return 折线图信息
    */

   List<Map<String,Object>> getUploadToMorrisJs(Long userId);

   /**
    * 给折线图传送用户下载信息
    * @param userId 用户id
    * @return 折线图信息
    */

    List<Map<String,Object>> getDownloadToMorrisJs(Long userId);

   /**
    * 根据用户id搜索上传次数
    * @param userId 用户id
    * @return 上传次数
    */
    Long getUploadTimesByCurrUser(Long userId);

   /**
    * 通过文件id获取文件名
    * @param localUrl 文件url
    * @return 文件id
    */
   Long getFileIdByUrl(String localUrl);

   /**
    * 通过文件名称获取文件id
    * @param fileName 文件名称
    * @return 文件id
    */
   Long getFileIdByFileName(String fileName);

   /**
    * 获取文件备份地址
    * @param filePath 文件地址
    * @return 文件备份地址
    */
   String getFileBackupUrl(String filePath);

   /**
    * 添加文件记录
    * @param fileRecord 文件记录
    * @return 结果
    */
   Boolean addFileRecord(FileRecord fileRecord);

   /**
    * 更新文件md5
    * @param fileId 文件id
    * @param fileMd5 文件md5
    * @param backupPath 备份文件地址
    * @return 结果
    */
   Boolean updateFileMd5(Long fileId, String fileMd5, String backupPath);

   /**
    * 获取用户上传的文件数目
    * @param userId 用户id
    * @return 文件数目
    */
   Integer getFileNumberById(Long userId);

   /**
    * 获取热门文件
    * @return 文件列表
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
