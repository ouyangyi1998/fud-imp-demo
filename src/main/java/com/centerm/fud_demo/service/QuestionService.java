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
            questionDTOList.add(questionDto);
        }
        pageDto.setData(questionDTOList);
        return pageDto;
    }

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


    public QuestionDTO getById(int id) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapper.getById(id);
        //把第一个对象的所有属性拷贝到第二个对象中
        BeanUtils.copyProperties(question, questionDTO);
        User user = userDao.findById(question.getCreateId());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void increaseView(int id) {
        questionMapper.updateView(id);
    }

    public List<Question> getByTag(int id, String result) {
        return questionMapper.getByTag(id,result);
    }

    public List<Question> getTopTen() {
        List<Question> questions=questionMapper.getTopTen();
        return questions;
    }


}
