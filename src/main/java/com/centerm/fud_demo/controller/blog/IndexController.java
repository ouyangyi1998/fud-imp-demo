package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.entity.Question;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.dto.PageDTO;
import com.centerm.fud_demo.mapper.NotificationMapper;
import com.centerm.fud_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 用户论坛主页控制
 * @author ouyangyi
 */
@Controller
public class IndexController {

    @Resource
    private QuestionService questionService;

    @Resource
    private NotificationMapper notificationMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 跳转到用户论坛主页
     * @param request 请求参数
     * @param model 模型
     * @param page 页数
     * @param size 分页长度
     * @return 用户主页
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size) {
        PageDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);

        //获取阅读量最高的十篇问题
        List<Question> questions = questionService.getTopTen();
        model.addAttribute("topQuestion",questions);

        //获取到user
        User user = (User)request.getSession().getAttribute("user");
        //获取未读的消息数量
        int unreadNum = notificationMapper.getUnreadCount(user.getId().intValue());
        request.getSession().setAttribute("unreadNum",unreadNum);
        return "blog/index";
    }

    /**
     * 用户图片传输
     * @param dir1 图片路径
     * @param dir2 图片路径
     * @param filename 图片名称
     * @return 照片信息
     */
    @GetMapping("/get/{dir1}/{dir2}/{filename}")
    public ResponseEntity get(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String filename){
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/" + dir1 + "/" + dir2 , filename);
        org.springframework.core.io.Resource resource = resourceLoader.getResource("file:" + path.toString());
        return ResponseEntity.ok(resource);
    }

}
