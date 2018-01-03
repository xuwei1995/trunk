package com.serviceindeed.yike.yikemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * sun add
 * Author:sun fei
 * create on:2017-12-04
 */
@Controller
public class LoginController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping({"/", "/login"})
    public String login() {
        /*Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:adminLTE/index";
        }*/
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "adminLTE/index";
    }

    @RequestMapping("/index2")
    public String index2() {
        return "adminLTE/index2";
    }
}
