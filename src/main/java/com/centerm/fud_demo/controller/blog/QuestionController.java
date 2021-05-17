package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.CommentDTO;
import com.centerm.fud_demo.entity.dto.QuestionDTO;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.service.CommentService;
import com.centerm.fud_demo.service.QuestionService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 用户帖子管理
 * @author ouyangyi
 * @time 2021.2.12
 */
@Slf4j
@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    private CommentService commentService;
    @Resource
    private NotificationMapper notificationMapper;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 用户访问帖子
     * @param id 帖子id
     * @param model 模型
     * @param request 请求参数
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")int id,
                           Model model,
                           HttpServletRequest request){
        //查找cookies，观察是否有token存在
        User user = (User)request.getSession().getAttribute("user");
        //获取未读的消息数量
        int unreadNum = notificationMapper.getUnreadCount(user.getId().intValue());
        request.getSession().setAttribute("unreadNum",unreadNum);
        QuestionDTO questionDTO = questionService.getById(id);
        //增加阅读数
        questionService.increaseView(id);
        model.addAttribute("questionDTO",questionDTO);
        //展示回复数据
        List<CommentDTO> comments = commentService.getById(id);
        model.addAttribute("comments",comments);
        //相关问题
        String[] tags = questionDTO.getTag().split(",");
        StringBuilder msg = new StringBuilder();
        for (String tag : tags){
            msg.append(tag);
            msg.append("|");
        }
        String result = msg.substring(0,msg.length()-1);
        log.info(result);
        List<Question> relativeQuestion = questionService.getByTag(id,result);
        model.addAttribute("relativeQuestion",relativeQuestion);

        return "blog/question";
    }

    /**
     * 用户图片传输
     * @param dir1 图片路径
     * @param dir2 图片路径
     * @param filename 图片名称
     * @return 图片文件
     */
    @GetMapping("/question/get/{dir1}/{dir2}/{filename}")
    public ResponseEntity get(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String filename){
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/" + dir1 + "/" + dir2 , filename);
        org.springframework.core.io.Resource resource = resourceLoader.getResource("file:" + path.toString());
        return ResponseEntity.ok(resource);
    }
}
