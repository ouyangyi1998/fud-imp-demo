package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.cache.TagCache;
import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.TagDTO;
import com.centerm.fud_demo.mapper.QuestionMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.centerm.fud_demo.constant.Constants.CREATE_QUESTION;

/**
 * 用户论坛发布帖子
 * @author ouyangyi
 * @time 2021.2.13
 */
@Controller
public class PublishController {

    @Resource
    private QuestionMapper questionMapper;

    /**
     * 进入发帖页面
     * @param model 模型
     * @return 页面
     */
    @GetMapping("/publish")
    public String publish(Model model) {
        //标签组
        TagCache tagCache = new TagCache();
        List<TagDTO> tags = tagCache.getTags();
        model.addAttribute("tags",tags);
        return "blog/publish";
    }

    /**
     * 发布帖子
     * @param title 帖子题目
     * @param description 帖子内容
     * @param tag 帖子Tag
     * @param id 操作帖子类型
     * @param request 请求参数
     * @param model 模型
     * @return 用户主页
     */
    @PostMapping("/publish")
    public String publishQuestion(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id",defaultValue = "-1")int id,
            HttpServletRequest request,
            Model model)
    {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        //标签组
        TagCache tagCache = new TagCache();
        List<TagDTO> tags = tagCache.getTags();
        model.addAttribute("tags",tags);
        //获取当前登陆用户的信息
        User user = (User)request.getSession().getAttribute("user");
        //将问题上传到数据库
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreateId(user.getId().intValue());
        question.setCreateTime(System.currentTimeMillis());
        if(id == CREATE_QUESTION){
            questionMapper.createQuestion(question);
        }else {
            question.setId(id);
            questionMapper.updateQuestion(question);
        }
        return "redirect:/index";
    }

    /**
     * 修改之前发出的帖子内容
     * @param id 帖子id
     * @param model 模型
     * @return 发帖页面
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")int id,
                       Model model){
        Question question = questionMapper.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        //用来标识问题是修改而不是重新创建
        model.addAttribute("id",question.getId());
        return "blog/publish";
    }
}
