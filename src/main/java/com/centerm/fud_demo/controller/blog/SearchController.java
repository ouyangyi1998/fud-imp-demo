package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.mapper.QuestionMapper;
import com.centerm.fud_demo.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ouyangyi
 */
@Controller
public class SearchController {
    @Resource
    private QuestionService questionService;

    @PostMapping("/search")
    public String searchQuestion(@RequestParam(name = "searchData")String search, @RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "size", defaultValue = "3") int size, Model model){
        PageDTO pagination = questionService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        //获取阅读量最高的十篇问题
        List<Question> questions= questionService.getTopTen();
        model.addAttribute("topQuestion",questions);
        return "blog/search";
    }

}
