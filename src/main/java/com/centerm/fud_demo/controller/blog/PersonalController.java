package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.NotificationDTO;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.service.NotificationService;
import com.centerm.fud_demo.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
}
