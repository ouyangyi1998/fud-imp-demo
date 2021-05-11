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

/**
 * @author ouyangyi
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

    @GetMapping("/personal/{action}")
    public String personal(@PathVariable(name = "action")String action,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "page",defaultValue = "1")int page,
                           @RequestParam(name = "size",defaultValue = "10")int size){
        User user = (User)request.getSession().getAttribute("user");
                    //获取未读的消息数量
                    int unreadNum=notificationMapper.getUnreadCount(user.getId().intValue());
                    request.getSession().setAttribute("unreadNum",unreadNum);
        if (action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            PageDTO<Question> pagination=questionService.list(user.getId().intValue(),page,size);
            model.addAttribute("pagination", pagination);
        }else if (action.equals("information")){
            model.addAttribute("section","information");
            model.addAttribute("sectionName","我的消息");
            PageDTO<NotificationDTO> notifications= notificationService.list(user.getId().intValue(),page,size);
            model.addAttribute("notifications",notifications);
        }

        return "blog/personal";
    }

    /**
     * 用户图片传输
     * @param dir1 图片路径
     * @param dir2 图片路径
     * @param filename 图片名称
     * @return
     */
    @GetMapping("/personal/get/{dir1}/{dir2}/{filename}")
    public ResponseEntity get(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String filename){
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/" + dir1 + "/" + dir2 , filename);
        org.springframework.core.io.Resource resource = resourceLoader.getResource("file:" + path.toString());
        return ResponseEntity.ok(resource);
    }
}
