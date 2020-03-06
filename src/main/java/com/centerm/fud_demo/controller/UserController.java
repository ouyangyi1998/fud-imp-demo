package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.exception.NotAcceptTermsException;
import com.centerm.fud_demo.exception.PasswordNotEqualsRetypePasswordException;
import com.centerm.fud_demo.exception.UsernameRepeatingException;
import com.centerm.fud_demo.service.DownloadService;
import com.centerm.fud_demo.service.FileService;
import com.centerm.fud_demo.service.UploadService;
import com.centerm.fud_demo.service.UserService;
import com.centerm.fud_demo.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户控制类
 * @author jerry
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    private User currUser = null;
    @Autowired
    private UserService userService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private FileService fileService;

    @GetMapping("toRegister")
    public String toRegister(){return "register";}
    @GetMapping("toUpload")
    public String toUploading()
    {
        return "user/upload";
    }
    @GetMapping("information")
    public String userInformation(HttpServletRequest request)
    {
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        Long uploadTimes = uploadService.getUploadTimesByCurrUser(user.getId());
        Long downloadTimes = downloadService.getDownloadTimesByUserId(user.getId());
        request.setAttribute("downloadTimes", downloadTimes);
        request.setAttribute("uploadTimes", uploadTimes);
        return "user/information";
    }
    @GetMapping("toLogin")
    public String toLogin()
    {
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if (null == user) {
            return "login";
        }else
        {
            return "redirect:/user/index";
        }
    }
    @GetMapping("filemanager")
    public String userFileManager(Model model)
    {
        List<FileRecord> fileRecordList = fileService.getFileByUserId(currUser.getId());
        model.addAttribute("fileList", fileRecordList);
        return "user/filemanager";
    }

    @GetMapping("download")
    public String userDownload(Model model)
    {
        List<FileRecord> fileRecordList = fileService.getFileByUserId(currUser.getId());
        model.addAttribute("fileList", fileRecordList);
        return "user/download";
    }

    @GetMapping("index")
    public String userIndex(Model model, HttpServletRequest request)
    {
        currUser = (User)request.getSession().getAttribute("user");
        Long currUserId = currUser.getId();
        List<FileRecord> mostDownloaded = downloadService.getMostDownloadRecordById(currUserId);
        Long downloadTimesByCurrUser = downloadService.getDownloadTimesByUserId(currUserId);
        Long uploadTimesByCurrUser = uploadService.getUploadTimesByCurrUser(currUserId);
        List<FileRecord> latestDownloaded = downloadService.getLatestDownloaded(currUserId);
        List<FileRecord> latestUploaded = uploadService.getLatestUploaded(currUserId);
        model.addAttribute("mostDownloaded", mostDownloaded);
        model.addAttribute("downloadTimesByCurrUser", downloadTimesByCurrUser);
        model.addAttribute("uploadTimesByCurrUser", uploadTimesByCurrUser);
        model.addAttribute("latestDownloaded", latestDownloaded);
        model.addAttribute("latestUploaded", latestUploaded);
        return "user/index";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public AjaxReturnMsg login(HttpServletRequest request)
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AjaxReturnMsg msg = new AjaxReturnMsg();
        User user = new User(username,password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        subject.login(token);
        String exception=(String)request.getAttribute("shiroLoginFailure");
        if (null == exception){
            log.info("User: " + username + " login successfully...");
            User toIndex = userService.findByUsername(username);
            request.getSession().setAttribute("user", toIndex);
            request.getSession().setAttribute("index",username.substring(0,1).toUpperCase());
            msg.setFlag(Constants.SUCCESS);
            msg.setUsername(username);
        }
        return msg;
    }
    @PostMapping("register")
    @ResponseBody
    public AjaxReturnMsg register(HttpServletRequest request)throws Exception
    {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rPassword = request.getParameter("r_password");
        String checkBox=request.getParameter("check");

        if (null == username || null == password || "" == username || "" == password) {
            log.warn("Neither username or password inputted...");
            throw new AuthenticationException();
        }
        if (!password.equals(rPassword))
        {
            log.warn("Password didn't match...");
            throw new PasswordNotEqualsRetypePasswordException();
        }
        if (!Constants.ACCEPT.equals(checkBox))
        {
            log.warn("Please check the agreement...");
            throw new NotAcceptTermsException();
        }
        User user = new User(username,password);
        User matching = userService.findByUsername(username);
        if (null == matching)
        {
            userService.createUser(user);
            log.info("User: " + username+" register successfully" + ", default permission is : USER");
            msg.setUsername(username);
            msg.setFlag(Constants.SUCCESS);
        }else
        {
            throw new UsernameRepeatingException();
        }
        return msg;
    }

    @PostMapping("information")
    @ResponseBody
    public AjaxReturnMsg updateUser(HttpServletRequest request){
        AjaxReturnMsg msg=new AjaxReturnMsg();
        String password = request.getParameter("password");
        String username=((User)request.getSession().getAttribute("user")).getUsername();
        if ((null == password || ("").equals(password)))
        {
            msg.setFlag(Constants.FAIL);
            msg.setMsg("No data submitted...");
            log.warn("No data submitted...");
            return msg;
        }
        if (!(null == password || ("").equals(password)))
        {
            userService.changePassword(username,password);
            log.info("Password update successfully...");
        }
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.getAuthenticationCache().remove(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());
        User user = userService.findByUsername(username);
        request.getSession().setAttribute("user",user);
        msg.setFlag(Constants.SUCCESS);
        msg.setMsg("Data update successfully...");
        return msg;
    }

    @RequestMapping("logout")
    @ResponseBody
    public ModelAndView logout(HttpServletRequest request)
    {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
    @PostMapping("search")
    @ResponseBody
    public AjaxReturnMsg search(HttpServletRequest request)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        String contents=request.getParameter("contents");
        Long userId=((User)request.getSession().getAttribute("user")).getId();
        List<FileRecord> fileList = fileService.getFileLikeContents(contents,userId);
        if (null == fileList || fileList.isEmpty())
        {
            msg.setMsg("No data matched...");
            msg.setFlag(Constants.FAIL);
            return msg;
        }
        request.getSession().setAttribute("contents",contents);
        msg.setFlag(Constants.SUCCESS);
        return msg;
    }
    @GetMapping("search")
    public String Search(HttpServletRequest request)
    {
        Long userId=((User)request.getSession().getAttribute("user")).getId();
        List<FileRecord> fileList= fileService.getFileLikeContents((String) request.getSession().getAttribute("contents"),userId);
        request.setAttribute("fileList",fileList);
        return "user/search";
    }

}
