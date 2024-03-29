package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.entity.FileForm;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.service.DownloadService;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.service.UploadService;
import com.centerm.fud_demo.utils.AesUtil;
import com.centerm.fud_demo.utils.GetDateUtil;
import com.centerm.fud_demo.utils.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

/**
 * 分片上传Controller
 * @author sheva
 * @time 2020.1.23
 */
@Controller
@Slf4j
@RequestMapping("file")
public class UploadController {


    @Autowired
    private UploadService uploadService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DownloadService downloadService;

    private static Map<Integer, String> keyMap = new HashMap<>(2);

    /**
     * 跳转到上传界面
     * @return 文件上传页面
     */
    @GetMapping("index")
    public String toUpload() {
        return "user/upload";
    }

    @PostMapping("isUpload")
    @ResponseBody
    public Map<String, Object> isUpload(@Valid FileForm form, HttpServletRequest request) throws Exception{
        User currUser = (User)request.getSession().getAttribute("user");
        log.info("需要上传的md5为： " + form.getMd5());
        Map<String, Object> map = new HashMap<>();
        map = uploadService.findByFileMd5(form.getMd5(), currUser.getId());
        if (2 != (int) map.get("flag")){
            keyMap = RsaUtil.genKeyPair();
            log.info("Public Key: " + keyMap.get(0));
            log.info("Private Key: " + keyMap.get(1));
            map.put("RsaPublicKey", keyMap.get(0));
        }
        return map;
    }

    /**
     *
     * @param form 文件分片信息
     * @param request 请求参数
     * @param fileEncrypt 文件分片
     * @param key rsa加密之后的秘钥
     * @return 文件分片上传信息
     */
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@Valid FileForm form, HttpServletRequest request,
                                      @RequestParam(value = "fileEncrypt", required = false)String fileEncrypt,
                                      @RequestParam(value = "key", required = false)String key
                                      ){
       User currUser = (User)request.getSession().getAttribute("user");
        Map<String, Object> map = null;
        try{
            if (Constants.CHECK.equals(form.getAction())){
                map = uploadService.check(form);
            }
            if (Constants.UPLOAD.equals(form.getAction())){
                log.info("rsa加密后的key：" + key);
                key = RsaUtil.decrypt(key, keyMap.get(1));
                log.info("解密后的key： " + key);
                String decrypt = AesUtil.decrypt(fileEncrypt, key);
                InputStream inputStream = new ByteArrayInputStream(AesUtil.hexToByte(decrypt));
                MultipartFile file = new MockMultipartFile(form.getName(), inputStream);

                map = uploadService.realUpload(form, file, currUser.getId());
            }
        }catch (Exception e){
            log.error("Upload error: " + e.getMessage());
        }
        return map;
    }

    /**
     * 用户删除文件
     * @param request 请求参数
     * @return 文件删除情况
     */
    @PostMapping("toDelete")
    @ResponseBody
    public AjaxReturnMsg toDelete(HttpServletRequest request) {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        Long fileId = Long.parseLong(request.getParameter("fileId"));
        User currUser = (User) request.getSession().getAttribute("user");
        log.info("Current user id is：" + currUser.getId());
        Boolean isSuccess = fileService.deleteFileById(currUser.getId(), fileId);
        downloadService.deleteDownloadRecord(fileId);
        if (!isSuccess)
        {
            msg.setFlag(Constants.FAIL);
            msg.setMsg("Delete Fail...");
            return msg;
        }
        msg.setFlag(Constants.SUCCESS);
        return msg;
    }

    /**
     * 修改文件范围为公有
     * @param request 请求参数
     * @return 操作信息
     */
    @PostMapping("changeFileScopeToPublic")
    @ResponseBody
    public AjaxReturnMsg changeFileScopeToPublic(HttpServletRequest request) {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        Long fileId=Long.parseLong(request.getParameter("fileId"));
        log.info("Change file: " + fileId + " to public");
        fileService.changeFileScopeToPublic(fileId);
        msg.setFlag(Constants.SUCCESS);
        msg.setMsg("文件范围修改为Public");
        return msg;
    }

    /**
     * 修改文件范围为私有
     * @param request 请求参数
     * @return 操作信息
     */
    @PostMapping("changeFileScopeToPrivate")
    @ResponseBody
    public AjaxReturnMsg changeFileScopeToPrivate(HttpServletRequest request) {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        Long fileId=Long.parseLong(request.getParameter("fileId"));
        log.info("Change file: " + fileId + " to private");
        fileService.changeFileScopeToPrivate(fileId);
        msg.setFlag(Constants.SUCCESS);
        msg.setMsg("文件范围修改为Private");
        return msg;
    }

    /**
     * 用户下载上传折线图
     * @param request 请求参数
     * @return 用户折线图
     */
    @PostMapping("getChart")
    @ResponseBody
    public List<Map<String,Object>> getChart(HttpServletRequest request)
    {
        Long userId = ((User)request.getSession().getAttribute("user")).getId();
        List<Map<String,Object>> uploadList = fileService.getUploadToMorrisJs(userId);
        List<Map<String,Object>> downloadList = fileService.getDownloadToMorrisJs(userId);

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<> (2);map1.put("days",GetDateUtil.getDate(7));map1.put("upload",0);map1.put("download",0);
        Map<String,Object> map2 = new HashMap<>(2);map2.put("days",GetDateUtil.getDate(6));map2.put("upload",0);map2.put("download",0);
        Map<String,Object> map3 = new HashMap<>(2);map3.put("days",GetDateUtil.getDate(5));map3.put("upload",0);map3.put("download",0);
        Map<String,Object> map4 = new HashMap<>(2);map4.put("days",GetDateUtil.getDate(4));map4.put("upload",0);map4.put("download",0);
        Map<String,Object> map5 = new HashMap<>(2);map5.put("days",GetDateUtil.getDate(3));map5.put("upload",0);map5.put("download",0);
        Map<String,Object> map6 = new HashMap<>(2);map6.put("days",GetDateUtil.getDate(2));map6.put("upload",0);map6.put("download",0);
        Map<String,Object> map7 = new HashMap<>(2);map7.put("days",GetDateUtil.getDate(1));map7.put("upload",0);map7.put("download",0);

        for (Map<String, Object> m : uploadList)
        {
               if(m.get("days").equals(map1.get("days"))){
                 map1.put("upload",m.get("upload"));
               }
               if(m.get("days").equals(map2.get("days"))){
                   map2.put("upload",m.get("upload"));
               }
               if(m.get("days").equals(map3.get("days"))){
                   map3.put("upload",m.get("upload"));
               }
               if(m.get("days").equals(map4.get("days"))){
                   map4.put("upload",m.get("upload"));
               }
               if(m.get("days").equals(map5.get("days"))){
                   map5.put("upload",m.get("upload"));
               }
               if(m.get("days").equals(map6.get("days"))){
                   map6.put("upload",m.get("upload"));
               }
               if(m.get("days").equals(map7.get("days"))){
                   map7.put("upload",m.get("upload"));
               }
        }
        for (Map<String, Object> m : downloadList)
        {
            if(m.get("days").equals(map1.get("days"))){
                map1.put("download",m.get("download"));
            }
            if(m.get("days").equals(map2.get("days"))){
                map2.put("download",m.get("download"));
            }
            if(m.get("days").equals(map3.get("days"))){
                map3.put("download",m.get("download"));
            }
            if(m.get("days").equals(map4.get("days"))){
                map4.put("download",m.get("download"));
            }
            if(m.get("days").equals(map5.get("days"))){
                map5.put("download",m.get("download"));
            }
            if(m.get("days").equals(map6.get("days"))){
                map6.put("download",m.get("download"));
            }
            if(m.get("days").equals(map7.get("days"))){
                map7.put("download",m.get("download"));
            }
        }
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        return list;
    }
}
