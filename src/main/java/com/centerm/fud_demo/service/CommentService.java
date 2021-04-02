package com.centerm.fud_demo.service;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.Comment;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.CommentDTO;
import com.centerm.fud_demo.mapper.CommentMapper;
import com.centerm.fud_demo.mapper.GreatMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ouyangyi
 */
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserDao userDao;
    @Resource
    private GreatMapper greatMapper;

    public List<CommentDTO> getById(int id) {
        //通过文章id找到所有回复
        List<Comment> comments=commentMapper.getById(id);
        //创建要给CommentDto的list
        List<CommentDTO> lists=new ArrayList<>();
        //遍历每个Comment
        for(Comment comment:comments){
            //找到回复人
            User user=userDao.findById(comment.getCommentor());
            System.out.println(user);
            CommentDTO commentDto=new CommentDTO();
            //将第一个元素复制到第二个元素中
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(user);
            int great = greatMapper.selectGreatbyQuestionAndComment(id,comment.getId(),user.getId().intValue());
            commentDto.setLikeCount(great);
            lists.add(commentDto);
        }
        return lists;
    }
}
