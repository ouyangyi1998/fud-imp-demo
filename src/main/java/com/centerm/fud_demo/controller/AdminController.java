package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.exception.AccountBanException;
import com.centerm.fud_demo.listener.Listener;
import com.centerm.fud_demo.service.*;
import com.centerm.fud_demo.shiro.UserRealm;
import com.centerm.fud_demo.utils.GetDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 管理员控制类
 * @author jerry
 */
@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private BackupService backupService;

    @GetMapping("file")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String adminDownload(HttpServletRequest request)
    {
        List<FileRecord> fileList=fileService.getAllFile();
        request.setAttribute("fileList",fileList);
        return "admin/filelist";
    }
    @GetMapping("index")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String adminIndex(ServletRequest request)
    {
        AtomicInteger userNum=Listener.sessionCount;
        long fileNums = uploadService.getUploadTimes();
        Long downloadTimes = downloadService.getDownloadTimes();
        List<FileRecord> fileRecordList = downloadService.getMostDownloadRecord();
        request.setAttribute("userNum",userNum);
        request.setAttribute("fileNums", fileNums);
        request.setAttribute("downloadTimes", downloadTimes);
        request.setAttribute("fileList", fileRecordList);
        return "admin/index";
    }
    @GetMapping("ban")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String adminBan(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        Long userId = user.getId();
        List<User> userList = adminService.getUserExceptAdminAndSuperVIP(userId);
        request.setAttribute("userList",userList);
        return "admin/ban";
    }

    @PostMapping("banUser")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    @ResponseBody
    public AjaxReturnMsg banUser(HttpServletRequest request) throws AccountBanException
    {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        String username = request.getParameter("username");
        User target = userService.findByUsername(username);
        Integer userState = target.getState();
        Long userId=target.getId();
       if(Constants.NORMAL.equals(userState))
       {
           //执行账号封禁
           Boolean isSuccess= adminService.banUser(userId);
           if (!isSuccess)
           {
               throw new AccountBanException();
           }
           log.info("User: " + username + "　has been locked...");
       }else {
           //执行账号解锁
           Boolean isSuccess = adminService.releaseUser(userId);
           if (!isSuccess)
           {
               throw new AccountBanException();
           }
           log.info("User: "+ username + " has been unlocked...");
       }
        DefaultWebSecurityManager securityManager= (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm shiroRealm = (UserRealm) securityManager.getRealms().iterator().next();
        shiroRealm.clearAllCache();
        msg.setFlag(Constants.SUCCESS);
        return msg;

    }

    /**
     * @param
     * @return
     */
    @PostMapping("toDelete")
    @ResponseBody
    public AjaxReturnMsg toDelete(HttpServletRequest request) {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        Long fileId = Long.parseLong(request.getParameter("fileId"));
        Boolean isSuccess=fileService.deleteFile(fileId);
        downloadService.deleteDownloadRecord(fileId);
        if (!isSuccess)
        {
            msg.setFlag(Constants.FAIL);
            msg.setMsg("Delete Failed...");
            return msg;
        }
        msg.setFlag(Constants.SUCCESS);
        return msg;
    }

    @PostMapping("search")
    @ResponseBody
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public AjaxReturnMsg search(HttpServletRequest request)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        String contents = request.getParameter("contents");
        List<User> userList= adminService.getUserLikeContents(contents);
        if (null == userList || userList.isEmpty())
        {
            msg.setMsg("No data...");
            msg.setFlag(Constants.FAIL);
            return msg;
        }

        request.getSession().setAttribute("contents",contents);
        msg.setFlag(Constants.SUCCESS);
        return msg;
    }
    @GetMapping("search")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    public String Search(HttpServletRequest request)
    {
        List<User> userList = adminService.getUserLikeContents((String) request.getSession().getAttribute("contents"));
        request.setAttribute("userList",userList);
        return "admin/search";
    }
    @PostMapping("getChart")
    @RequiresRoles(value = {"ADMIN","SUPERVIP"},logical = Logical.OR)
    @ResponseBody
    public List<Map<String,Object>> getChart(HttpServletRequest request)
    {
        List<Map<String,Object>> uploadList= adminService.getAllUploadToMorrisJs();
        List<Map<String,Object>> downloadList= adminService.getAllDownloadToMorrisJs();


        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();map1.put("days", GetDateUtil.getDate(7));map1.put("upload",0);map1.put("download",0);
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
    @PostMapping("getC3Chart")
    @ResponseBody
    public Map<String,Object> getC3Chart()
    {
        long allUserNumber = adminService.getAllUserNumber();
        long adminNumber = adminService.getAdminNumber();
        log.info("Admin number is： " + adminNumber);
        Map<String,Object> map=new HashMap<>();
        map.put("user",((allUserNumber - adminNumber) * 100) / allUserNumber);
        map.put("admin",(100 * adminNumber) / allUserNumber);
        return map;
    }

}
