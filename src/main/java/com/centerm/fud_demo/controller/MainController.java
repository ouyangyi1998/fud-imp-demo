package com.centerm.fud_demo.controller;

import com.centerm.fud_demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ouyangyi
 */
@Controller
public class MainController {
    @RequestMapping("/")
    public String tologin(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        if (null == user) {
            return "login";
        }else
        {
            return "redirect:/user/index";
        }
    }
}
