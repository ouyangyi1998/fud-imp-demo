package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.entity.FileRecord;
import com.centerm.fud_demo.entity.UpdateForm;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.centerm.fud_demo.constant.Constants.*;

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
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("toView")
    public String toView(){return "view";}

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
        request.setAttribute("headPicutre",user.getHeadPicture());
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
        if (null == username || null == password || "".equals(username) || "".equals(password))
        {
            log.warn("Neither username or password inputted...");
            throw new AuthenticationException();
        }
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
    public AjaxReturnMsg updateUser( HttpServletRequest request, UpdateForm form) throws IOException {
        AjaxReturnMsg msg = new AjaxReturnMsg();
        String password = form.getPassword();
        String username = ((User)request.getSession().getAttribute("user")).getUsername();
        MultipartFile headPicture = form.getFile();
        //更新密码为空
        if ((null == password) && (headPicture == null))
        {
            msg.setFlag(Constants.FAIL);
            msg.setMsg("No data submitted...");
            log.warn("No data submitted...");
            return msg;
        }
        if (password != null && password.trim().length() != 0){
            userService.changePassword(username,password);
            log.info(username + " Password update successfully...");
        }
        if (headPicture != null){
            //配置文件名称
            String fileName = username + "_" + System.currentTimeMillis() + headPicture.getOriginalFilename().substring(headPicture.getOriginalFilename().lastIndexOf("."));
            //把文件输入到后端文件夹
            String dirPath = System.getProperty("user.dir") + "/src/main/resources/static/images/headPictures/";
            File dir = new File(dirPath);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File dest = new File(dir,fileName);
            headPicture.transferTo(dest);
            //数据库录入
            String filePath = "/images/headPictures/" + fileName;
            //找到之前的文件删除
            String oldHeadPicture = userService.selectHeadPcitureURL(username);
            File old = new File(System.getProperty("user.dir")+ "/src/main/resources/static" + oldHeadPicture);
            if (old.exists()){
                old.delete();
                System.out.println("头像删除成功");
            }
            userService.updateHeadPicture(username,filePath);
            log.info(username + " HeadPicture update successfully...");
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

    @PostMapping("getC3Chart")
    @ResponseBody
    public Map<String,Object> getC3Chart(HttpServletRequest request)
    {
        Long userId=((User)request.getSession().getAttribute("user")).getId();
        Integer userFileNumber = fileService.getFileNumberById(userId);
        log.info("file number is： " + userFileNumber);
        List<FileRecord> userUploadFile = fileService.getFileByUserId(userId);

        float vedioNumber = 0;
        float audioNumber = 0;
        float documentNumber = 0;
        float pictureNumber = 0;

        for (FileRecord fileRecord : userUploadFile){
            String suffix = fileRecord.getSuffix();
            for(String audioSuffix:AUDIO_LIST){
                if (suffix.toLowerCase().contains(audioSuffix.toLowerCase())){
                    audioNumber++;
                    break;
                }
            }
            for(String vedioSuffix:VEDIO_LIST){
                if (suffix.contains(vedioSuffix)){
                    vedioNumber++;
                    break;
                }
            }
            for(String documentSuffix:DOCUMENT_LIST){
                if (suffix.contains(documentSuffix)){
                    documentNumber++;
                    break;
                }
            }
            for(String pictureSuffix:PICTURE_LIST){
                if (suffix.toLowerCase().contains(pictureSuffix.toLowerCase())){
                    pictureNumber++;
                    break;
                }
            }
        }

        Map<String,Object> map=new HashMap<>();
        map.put("vedio",vedioNumber/userFileNumber);
        map.put("audio",audioNumber/userFileNumber);
        map.put("document",documentNumber/userFileNumber);
        map.put("picture",pictureNumber/userFileNumber);
        map.put("other",1-vedioNumber/userFileNumber-audioNumber/userFileNumber-documentNumber/userFileNumber-pictureNumber/userFileNumber);
        return map;
    }

    /**
     * 用户图片传输
     * @param dir1 图片路径
     * @param dir2 图片路径
     * @param filename 图片名称
     * @return
     */
    @GetMapping("/get/{dir1}/{dir2}/{filename}")
    public ResponseEntity get(@PathVariable String dir1,@PathVariable String dir2,@PathVariable String filename){
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/" + dir1 + "/" + dir2 , filename);
        Resource resource = resourceLoader.getResource("file:" + path.toString());
        return ResponseEntity.ok(resource);
    }

}
