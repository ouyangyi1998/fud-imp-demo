package com.centerm.fud_demo.service;

import com.centerm.fud_demo.dao.UserDao;
import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.entity.dto.QuestionDTO;
import com.centerm.fud_demo.mapper.GreatMapper;
import com.centerm.fud_demo.mapper.QuestionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 论坛帖子管理
 * @author ouyangyi
 */
@Service
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private GreatMapper greatMapper;

    @Resource
    private UserDao userDao;

    /**
     * 帖子分页
     * @param page 页面数
     * @param size 页面长度
     * @return 分页信息
     */
    public PageDTO list(int page, int size) {
        PageDTO pageDto = new PageDTO();
        int totalCount = questionMapper.count();

        pageDto.setPagination(totalCount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.list(offset, size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userDao.findById(question.getCreateId());
            QuestionDTO questionDto = new QuestionDTO();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            int greatNum = greatMapper.getAllGreatByQuestion(question.getId());
            questionDto.setLikeCount(greatNum);
            questionDTOList.add(questionDto);
        }
        pageDto.setData(questionDTOList);
        return pageDto;
    }

    /**
     * 帖子分页
     * @param userId 用户id
     * @param page 页面数
     * @param size 页面长度
     * @return 分页信息
     */
    public PageDTO list(int userId, int page, int size) {
        PageDTO pageDto = new PageDTO();
        int totalCount = questionMapper.countById(userId);
        pageDto.setPagination(totalCount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.listById(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userDao.findById(question.getCreateId());
            QuestionDTO questionDto = new QuestionDTO();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDto.setLikeCount(greatMapper.getAllGreatByQuestion(question.getId()));
            questionDTOList.add(questionDto);
        }
        pageDto.setData(questionDTOList);
        return pageDto;
    }

    /**
     * 搜索帖子数据分页
     * @param search 搜索关键词
     * @param page 分页数
     * @param size 分页长度
     * @return 分页信息
     */
    public PageDTO list(String search, int page, int size) {
        PageDTO pageDto = new PageDTO();
        int totalCount = questionMapper.countBySearch("%" + search + "%");
        pageDto.setPagination(totalCount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.listBySearch("%" + search + "%",offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userDao.findById(question.getCreateId());
            QuestionDTO questionDto = new QuestionDTO();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDTOList.add(questionDto);
        }
        pageDto.setData(questionDTOList);
        return pageDto;
    }

    /**
     * 根据id获取帖子信息
     * @param id 帖子id
     * @return 帖子信息
     */
    public QuestionDTO getById(int id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question=questionMapper.getById(id);
        //把第一个对象的所有属性拷贝到第二个对象中
        BeanUtils.copyProperties(question, questionDTO);
        User user = userDao.findById(question.getCreateId());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 增加点击数
     * @param id 帖子id
     */
    public void increaseView(int id) {
        questionMapper.updateView(id);
    }

    /**
     * 通过Tag关联其他帖子
     * @param id 帖子id
     * @param result 关键词
     * @return 帖子信息
     */
    public List<Question> getByTag(int id, String result) {
        return questionMapper.getByTag(id,result);
    }

    /**
     * 获取前十的帖子
     * @return 帖子信息
     */
    public List<Question> getTopTen() {
        return questionMapper.getTopTen();
    }


}
