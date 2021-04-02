package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.CommentDTO;
import com.centerm.fud_demo.entity.dto.QuestionDTO;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.service.CommentService;
import com.centerm.fud_demo.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ouyangyi
 */
@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    private CommentService commentService;
    @Resource
    private NotificationMapper notificationMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")int id,
                           Model model,
                           HttpServletRequest request){
        //查找cookies，观察是否有token存在
        User user = (User)request.getSession().getAttribute("user");
        //获取未读的消息数量
        int unreadNum=notificationMapper.getUnreadCount(user.getId().intValue());
        request.getSession().setAttribute("unreadNum",unreadNum);
        QuestionDTO questionDTO=questionService.getById(id);
        //增加阅读数
        questionService.increaseView(id);
        model.addAttribute("questionDTO",questionDTO);
        //展示回复数据
        List<CommentDTO> comments=commentService.getById(id);
        model.addAttribute("comments",comments);
        //相关问题
        String[] tags=questionDTO.getTag().split(",");
        StringBuilder msg=new StringBuilder();
        for (String tag:tags){
            msg.append(tag);
            msg.append("|");
        }
        String result=msg.substring(0,msg.length()-1);
        System.out.println(result);
        List<Question> relativeQuestion =questionService.getByTag(id,result);
        model.addAttribute("relativeQuestion",relativeQuestion);

        return "blog/question";
    }
}
