package com.centerm.fud_demo.controller;
import com.centerm.fud_demo.constant.Constants;
import com.centerm.fud_demo.entity.User;
import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import com.centerm.fud_demo.service.SuperVipService;
import com.centerm.fud_demo.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 超级管理员控制类
 * @author jerry
 */
@Controller
@RequestMapping("supervip")
@Slf4j
public class SuperVipController {

    @Autowired
    private SuperVipService superVipService;

    @GetMapping("permission")
    @RequiresRoles(value = "SUPERVIP")
    public String permission(ServletRequest request)
    {
        List<User> userList = superVipService.getAllUserExceptSuperVIP();
        request.setAttribute("userList",userList);
        return "supervip/permission";
    }

    @RequestMapping("/handleAdmin")
    @RequiresRoles(value = "SUPERVIP")
    @ResponseBody
    public AjaxReturnMsg handleAdmin(ServletRequest request)
    {
        log.info("Handling admin...");
        AjaxReturnMsg msg=new AjaxReturnMsg();
        Long userId = Long.parseLong(request.getParameter("userId"));
        log.info("User id: " + userId);
        if (superVipService.getUserRoles(userId) == Constants.ADMIN)
        {
            superVipService.removeAdmin(userId);
        }else if (superVipService.getUserRoles(userId) == Constants.USER)
        {

            superVipService.becomeAdmin(userId);
        }
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm)securityManager.getRealms().iterator().next();
        userRealm.clearAllCache();
        msg.setFlag(Constants.SUCCESS);
        return msg;
    }
    /**
     * @param
     * @return
     */
    @PostMapping("delete")
    @RequiresRoles(value = "SUPERVIP")
    @ResponseBody
    public AjaxReturnMsg deleteUser(HttpServletRequest request) {
        Long userId=Long.valueOf(request.getParameter("userId"));
        AjaxReturnMsg msg = new AjaxReturnMsg();
        superVipService.removeUser(userId);
        msg.setFlag(Constants.SUCCESS);
        return msg;
    }

}
