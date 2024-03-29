package com.centerm.fud_demo.exception;

import com.centerm.fud_demo.entity.ajax.AjaxReturnMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目异常类管理
 * @author ouyangyi
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    public String unauthorizedExceptionHandler(HttpServletRequest request, UnauthorizedException ex)
    {

        log.warn("IP:"+request.getRemoteAddr()+" 无权限");
        return "/user/index";
    }

    @ExceptionHandler(value = UnknownAccountException.class)
    @ResponseBody
    public AjaxReturnMsg unknownAccountExceptionHandler(HttpServletRequest request, UnknownAccountException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("用户名密码错误");
        log.warn("IP: "+request.getRemoteAddr()+" 登录失败"+",原因：用户名密码错误");
        return msg;
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    @ResponseBody
    public AjaxReturnMsg incorrectCredentialsExceptionHandler(HttpServletRequest request, IncorrectCredentialsException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("用户名密码有误");
        log.warn("IP"+request.getRemoteAddr()+" 登录失败"+",原因：用户名密码有误");
        return msg;
    }

    @ExceptionHandler(value = LockedAccountException.class)
    @ResponseBody
    public AjaxReturnMsg lockedAccountExceptionHandler(HttpServletRequest request, LockedAccountException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("账号被锁定");
        log.warn("IP"+request.getRemoteAddr()+" 登录失败"+",原因：账号锁定");
        return msg;
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public AjaxReturnMsg authenticationExceptionHandler(HttpServletRequest request, AuthenticationException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("未输入用户名或密码");
        log.warn("IP"+request.getRemoteAddr()+" 登录失败"+",原因：未输入用户名或密码");
        return msg;
    }

    @ExceptionHandler(value = PasswordNotEqualsRetypePasswordException.class)
    @ResponseBody
    public AjaxReturnMsg passwordNotEqualsRetypePasswordExceptionHandler(HttpServletRequest request, PasswordNotEqualsRetypePasswordException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("注册密码与重复输入不匹配");
        log.warn("IP"+request.getRemoteAddr()+" 注册失败"+",原因：注册密码与重复输入不匹配");
        return msg;
    }

    @ExceptionHandler(value = NotAcceptTermsException.class)
    @ResponseBody
    public AjaxReturnMsg notAcceptTermsExceptionHandler(HttpServletRequest request, NotAcceptTermsException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("不同意我方license");
        log.warn("IP"+request.getRemoteAddr()+" 注册失败"+",原因：不同意我方license");
        return msg;
    }

    @ExceptionHandler(value = UsernameRepeatingException.class)
    @ResponseBody
    public AjaxReturnMsg usernameRepeatingExceptionHandler(HttpServletRequest request, UsernameRepeatingException ex)
    {
        AjaxReturnMsg msg=new AjaxReturnMsg();
        msg.setFlag(0);
        msg.setMsg("用户名已注册");
        log.warn("IP"+request.getRemoteAddr()+" 注册失败"+",原因：用户名已注册");
        return msg;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request)
    {
        log.warn("用户 "+request.getRemoteAddr()+" 进行非法的请求访问");
        return "error/405";
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public String httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request)
    {
        log.warn("用户 "+request.getRemoteAddr()+" 不支持媒体类型");
        return "error/415";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundExceptionHandler(HttpServletRequest request)
    {

        log.warn("用户 "+request.getRemoteAddr()+" 操作未找到访问资源");
        return "error/404";
    }

}
