package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.enums.NotificationEnum;
import com.centerm.fud_demo.mapper.CommentMapper;
import com.centerm.fud_demo.mapper.NotificationMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户消息提醒模块
 * @author ouyangyi
 * @time 2021.2.12
 */
@Controller
public class NotificationController {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private CommentMapper commentMapper;

    /**
     * 用户点击消息提醒处理
     * @param id 消息提醒id
     * @param request 请求参数
     * @return 请求帖子页面
     */
    @GetMapping("/notification/{action}")
    public String notification(@PathVariable("action")int id,
                               HttpServletRequest request){
        //将通知设置为已读
        notificationMapper.updateStatus(id);
        //获取type，检验是回复评论还是回复问题 1=回复评论 2=回复问题
        int type = notificationMapper.getTypeById(id);
        int outerId = notificationMapper.getOuterIdById(id);
        int questionId;
        if(type == NotificationEnum.NOTIFICATION_QUESTION.getType()){
            questionId = outerId;
        }else {
            int fatherCommentId = commentMapper.getParentIdById(id);
            questionId = commentMapper.getParentIdById(fatherCommentId);
        }
        return "redirect:/question/"+questionId;
    }
}
