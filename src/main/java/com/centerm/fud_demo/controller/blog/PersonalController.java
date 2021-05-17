package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.NotificationDTO;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.service.NotificationService;
import com.centerm.fud_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.centerm.fud_demo.constant.Constants.INFORMATION;
import static com.centerm.fud_demo.constant.Constants.QUESTIONS;
import static org.apache.coyote.http11.Constants.QUESTION;

/**
 * 用户个人信息处理
 * @author ouyangyi
 * @time 2021.2.13
 */
@Controller
public class PersonalController {

    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationService notificationService;
    @Resource
    private NotificationMapper notificationMapper;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 个人消息问题处理
     * @param action 个人消息类型
     * @param model 模型
     * @param request 请求参数
     * @param page 分页数
     * @param size 分页长度
     * @return 页面
     */
    @GetMapping("/personal/{action}")
    public String personal(@PathVariable(name = "action")String action,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "page",defaultValue = "1")int page,
                           @RequestParam(name = "size",defaultValue = "10")int size){
        User user = (User)request.getSession().getAttribute("user");
                    //获取未读的消息数量
                    int unreadNum = notificationMapper.getUnreadCount(user.getId().intValue());
                    request.getSession().setAttribute("unreadNum",unreadNum);
        if (QUESTIONS.equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            PageDTO<Question> pagination = questionService.list(user.getId().intValue(),page,size);
            model.addAttribute("pagination", pagination);
        }else if (INFORMATION.equals(action)){
            model.addAttribute("section","information");
            model.addAttribute("sectionName","我的消息");
            PageDTO<NotificationDTO> notifications = notificationService.list(user.getId().intValue(),page,size);
            model.addAttribute("notifications",notifications);
        }

        return "blog/personal";
    }

    /**
     * 用户图片传输
     * @param dir1 图片路径
     * @param dir2 图片路径
     * @param filename 图片名称
     * @return 照片信息
     */
    @GetMapping("/personal/get/{dir1}/{dir2}/{filename}")
    public ResponseEntity get(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String filename){
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/" + dir1 + "/" + dir2 , filename);
        org.springframework.core.io.Resource resource = resourceLoader.getResource("file:" + path.toString());
        return ResponseEntity.ok(resource);
    }
}
