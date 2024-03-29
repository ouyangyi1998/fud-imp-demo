package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.DownloadRecord;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.service.DownloadService;
import com.centerm.fud_demo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载控制
 * @author Sheva
 * @version 1.0
 * @date 2020/1/31 下午12:41
 */
@Controller
@ResponseBody
@Slf4j
@RequestMapping("download")
public class DownloadController{

    @Autowired
    private DownloadService downloadService;
    @Autowired
    private FileService fileService;

    /**
     * 用户下载文件
     * @param id 文件id
     * @param response 响应参数
     * @return 用户下载页面
     */
    @GetMapping("toDownload")
    public String toDownload(Long id, HttpServletResponse response, HttpServletRequest request) throws Exception{
        User currUser = (User) request.getSession().getAttribute("user");
        log.info("User: " + currUser.getUsername() + "  is downloading file(id)： " + id);
        downloadService.downloadFile(id, response);
        DownloadRecord downloadRecord = new DownloadRecord(currUser.getId(), id);
        downloadService.addDownloadRecord(downloadRecord);
        fileService.updateFile(id);
        return "user/download";
    }
}
