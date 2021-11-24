package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 论坛帖子查询模块
 * @author ouyangyi
 * @time 2021.2.14
 */
@Controller
public class SearchController {
    @Resource
    private QuestionService questionService;

    /**
     * 用户查询帖子
     * @param search 查询关键词
     * @param page 页面数
     * @param size 页面长度
     * @param model 模型
     * @return 页面
     */
    @PostMapping("/search")
    public String searchQuestion(@RequestParam(name = "searchData")String search, @RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "size", defaultValue = "3") int size, Model model){
        PageDTO pagination = questionService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        //获取阅读量最高的十篇问题
        List<Question> questions = questionService.getTopTen();
        model.addAttribute("topQuestion",questions);
        return "blog/search";
    }

}
