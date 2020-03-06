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
 */
@Controller
@Slf4j
@RequestMapping("file")
public class UploadController {

    private User currUser = null;

    @Autowired
    private UploadService uploadService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DownloadService downloadService;
    /**
     * 跳转到上传界面
     * @return
     */
    @GetMapping("index")
    public String toUpload() {
        return "user/upload";
    }

    @PostMapping("isUpload")
    @ResponseBody
    public Map<String, Object> isUpload(@Valid FileForm form, HttpServletRequest request){
        currUser = (User)request.getSession().getAttribute("user");
        log.info("需要上传的md5为： " + form.getMd5());
        return uploadService.findByFileMd5(form.getMd5(), currUser.getId());
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@Valid FileForm form, HttpServletRequest request,
                                      @RequestParam(value = "fileEncrypt", required = false)String fileEncrypt,
                                      @RequestParam(value = "key", required = false)String key
                                      )throws Exception{
        currUser = (User)request.getSession().getAttribute("user");
        Map<String, Object> map = null;
        try{
            if (Constants.CHECK.equals(form.getAction())){
                map = uploadService.check(form);
            }
            if (Constants.UPLOAD.equals(form.getAction())){
                log.info("key = " + key);
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
     * @param
     * @return
     */
    @PostMapping("toDelete")
    @ResponseBody
    public AjaxReturnMsg toDelete(HttpServletRequest request) {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        Long fileId=Long.parseLong(request.getParameter("fileId"));
        currUser = (User) request.getSession().getAttribute("user");
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
    @PostMapping("getChart")
    @ResponseBody
    public List<Map<String,Object>> getChart(HttpServletRequest request)
    {
        System.out.println("123");
        Long userId=((User)request.getSession().getAttribute("user")).getId();
        List<Map<String,Object>> uploadList= fileService.getUploadToMorrisJs(userId);
        List<Map<String,Object>> downloadList= fileService.getDownloadToMorrisJs(userId);


        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();map1.put("days",GetDateUtil.getDate(7));map1.put("upload",0);map1.put("download",0);
        Map<String,Object> map2=new HashMap<>();map2.put("days",GetDateUtil.getDate(6));map2.put("upload",0);map2.put("download",0);
        Map<String,Object> map3=new HashMap<>();map3.put("days",GetDateUtil.getDate(5));map3.put("upload",0);map3.put("download",0);
        Map<String,Object> map4=new HashMap<>();map4.put("days",GetDateUtil.getDate(4));map4.put("upload",0);map4.put("download",0);
        Map<String,Object> map5=new HashMap<>();map5.put("days",GetDateUtil.getDate(3));map5.put("upload",0);map5.put("download",0);
        Map<String,Object> map6=new HashMap<>();map6.put("days",GetDateUtil.getDate(2));map6.put("upload",0);map6.put("download",0);
        Map<String,Object> map7=new HashMap<>();map7.put("days",GetDateUtil.getDate(1));map7.put("upload",0);map7.put("download",0);

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
