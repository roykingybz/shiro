package com.royking.shiro.controller;

import com.royking.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author YIN
 * @Title: LoginController
 * @Package com.royking.shiro.controller
 * @Description:
 * @date 2018/10/621:32
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user) {

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return "密码错误";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return "用户名错误";
        }

        // 编程式验证权限
        String resultStr = "";
        if (subject.hasRole("admin")) {
            resultStr = "有admin的权限";
        } else {
            resultStr = "无admin的权限";
        }

        try {
            subject.checkPermissions("user:select");
            resultStr += ", 拥有user:select权限";
        } catch (Exception e) {
            resultStr += ", 没有user:select权限";
            e.printStackTrace();
        }

        return resultStr;
    }

    /**
     * 注解式验证权限
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/checkRoles" ,method = RequestMethod.GET)
    @ResponseBody
    public String checkRoles() {
        return "checkRole success";
    }

    /**
     * 测试默认的filter拦截器，spring.xml中roles["admin"]
     * @return
     */
    @RequestMapping(value = "/checkRoles1" ,method = RequestMethod.GET)
    @ResponseBody
    public String checkRoles1() {
        return "checkRole1 success";
    }

    /**
     * 测试默认的filter拦截器
     * @return
     */
    @RequestMapping(value = "/checkRoles2" ,method = RequestMethod.GET)
    @ResponseBody
    public String checkRoles2() {
        return "checkRole2 success";
    }

    /**
     * 测试默认的filter拦截器
     * @return
     */
    @RequestMapping(value = "/checkPerms1" ,method = RequestMethod.GET)
    @ResponseBody
    public String checkPerms1() {
        return "checkPerms1 success";
    }

    /**
     * 测试默认的filter拦截器
     * @return
     */
    @RequestMapping(value = "/checkPerms2" ,method = RequestMethod.GET)
    @ResponseBody
    public String checkPerms2() {
        return "checkPerms2 success";
    }

    /**
     * 测试自定义filter
     * @return
     */
    @RequestMapping(value = "/checkRoles3" ,method = RequestMethod.GET)
    @ResponseBody
    public String checkRoles3() {
        return "checkRole3 success";
    }

}
