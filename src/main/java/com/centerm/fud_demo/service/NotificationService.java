package com.centerm.fud_demo.service;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.Comment;
import com.centerm.fud_demo.entity.Notification;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.NotificationDTO;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.enums.NotificationEnum;
import com.centerm.fud_demo.mapper.CommentMapper;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ouyangyi
 */
@Service
public class NotificationService {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private UserDao userDao;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private CommentMapper commentMapper;

    //返回一个PageDto
    public PageDTO list(int id, int page, int size) {
        PageDTO pageDto = new PageDTO();
        int totalCount = notificationMapper.count(id);
        pageDto.setPagination(totalCount, page, size);
        int offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.list(id, offset, size);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        //将notification插入到notificationDto中，再将user信息也插入到notificationDto中
        //最后插入到notificationDtoList列表里
        for (Notification notification : notifications) {
            User user = userDao.findById(notification.getNotifier());
            NotificationDTO notificationDto = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setNotifier(user);
            String outerContent;
            if (notification.getType() == NotificationEnum.NOTIFICATION_QUESTION.getType()) {
                System.out.println(notification);
                outerContent = questionMapper.getTitleById(notification.getOuterId());
                //插入问题的id
                notificationDto.setQuestionId(notification.getOuterId());
            } else {
                outerContent = commentMapper.getContentById(notification.getOuterId());
                //插入问题的id
                Comment comment=commentMapper.getParentById(notification.getOuterId());
                notificationDto.setQuestionId(comment.getParentId());
            }
            notificationDto.setOuterContent(outerContent);
            notificationDTOList.add(notificationDto);
        }
        //在pageDto中插入notificationDtoList
        pageDto.setData(notificationDTOList);
        return pageDto;
    }
}
