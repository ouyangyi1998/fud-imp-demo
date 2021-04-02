package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.Comment;
import com.centerm.fud_demo.entity.Notification;
import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.CommentCreateDTO;
import com.centerm.fud_demo.entity.dto.CommentDTO;
import com.centerm.fud_demo.entity.dto.ResultDTO;
import com.centerm.fud_demo.enums.NotificationEnum;
import com.centerm.fud_demo.enums.NotificationStatusEnum;
import com.centerm.fud_demo.mapper.CommentMapper;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ouyangyi
 */
@Controller
public class CommentController {
    @Resource
    private UserDao userDao;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private NotificationMapper notificationMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //获取到user
        User user = (User)request.getSession().getAttribute("user");
        //获取未读的消息数量
        int unreadNum=notificationMapper.getUnreadCount(user.getId().intValue());
        request.getSession().setAttribute("unreadNum",unreadNum);

        //把评论插入数据库
        Comment comment=new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setCommentor(user.getId().intValue());
        System.out.println("comment:"+comment);
        commentMapper.insert(comment);

        if (commentCreateDTO.getType() == 2){
            //把回复评论的通知插入数据库
            Notification notification=new Notification();
            notification.setNotifier(comment.getCommentor());
            Comment comment2=commentMapper.getParentById(commentCreateDTO.getParentId());
            notification.setReceiver(comment2.getCommentor());
            notification.setOuterId(commentCreateDTO.getParentId());
            notification.setType(NotificationEnum.NOTIFICATION_COMMENT.getType());
            notification.setCreateTime(System.currentTimeMillis());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationMapper.insert(notification);

            //增加评论数
            commentMapper.updateCommentCount(commentCreateDTO.getParentId());
        }else {
            //把回复问题的通知插入数据库
            Question question=questionMapper.getById(commentCreateDTO.getParentId());
            Notification notification=new Notification();
            notification.setNotifier(user.getId().intValue());
            notification.setReceiver(question.getCreateId());
            notification.setOuterId(commentCreateDTO.getParentId());
            notification.setType(NotificationEnum.NOTIFICATION_QUESTION.getType());
            notification.setCreateTime(System.currentTimeMillis());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationMapper.insert(notification);
            //增加问题回复量
            questionMapper.updateComment(commentCreateDTO.getParentId());
        }
        ResultDTO resultDto=new ResultDTO();
        return resultDto.success();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") int id,
                                                HttpServletRequest request){
        //查找type=2，即是回复评论的评论
        List<Comment> comments = commentMapper.getCommentById(id,2);
        List<CommentDTO> commentDto=new ArrayList<>();

        //找到User
        User user = (User)request.getSession().getAttribute("user");
        int userId = user.getId().intValue();
        User userToFront = userDao.findById(userId);

        //把二级评论和对应的User写进每个CommentDto集合中
        for (Comment comment:comments){
            CommentDTO dto=new CommentDTO();
            BeanUtils.copyProperties(comment,dto);
            dto.setUser(userToFront);
            commentDto.add(dto);
        }
        ResultDTO resultDto=new ResultDTO();
        //返回数据给前端
        return resultDto.success(commentDto);
    }
}
