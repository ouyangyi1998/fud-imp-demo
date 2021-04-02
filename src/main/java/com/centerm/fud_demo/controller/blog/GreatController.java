package com.centerm.fud_demo.controller.blog;

import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.mapper.GreatMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ouyangyi
 */
@Controller
public class GreatController {
    @Resource
    private GreatMapper greatMapper;

    @ResponseBody
    @RequestMapping(value = "/increaseGreat",method = RequestMethod.POST)
    public AjaxReturnMsg increaseGreat(@RequestParam(name = "questionId")int questionId, @RequestParam(name = "commentId")int commentId, HttpServletRequest request, Model model){
        AjaxReturnMsg msg = new AjaxReturnMsg();
        //获取到user
        User user = (User)request.getSession().getAttribute("user");
        //检查之前是否点赞过
        int res = greatMapper.selectGreatbyQuestionAndComment(questionId,commentId,user.getId().intValue());
        System.out.println(res);
        System.out.println(questionId+"  "+commentId);
        if (res<=0){
            //未点赞
            greatMapper.insertGreatByQuestionAndComment(questionId,commentId,user.getId().intValue());
        }else{
            //点赞了 取消点赞
            greatMapper.removeGreatByQuestionAndComment(questionId,commentId,user.getId().intValue());
        }
        msg.setFlag(1);
        return msg;
    }
}
