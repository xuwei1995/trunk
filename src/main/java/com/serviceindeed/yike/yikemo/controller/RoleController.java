package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.Role;
import com.serviceindeed.yike.yikemo.domain.RoleFunction;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.service.RoleService;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("auth/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    Logger log= LoggerFactory.getLogger(this.getClass()) ;
    @RequestMapping("/getAllRoles")
    @ResponseBody
    public Map getAllRoles(HttpPages httpPages,Role role)
    {
        try {
            return  roleService.getAllRoles(httpPages,role);
        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }
    @RequestMapping("/addRole")
    @ResponseBody
    public Map addRole(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, Role role, RoleFunction roleFunction)
    {
        try {
            return  roleService.addRole(userToken,headers,role,roleFunction);
        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        }

    }
    @RequestMapping("/editRole")
    @ResponseBody
    public Map editRole(@AuthenticationPrincipal User userToken, @RequestHeader HttpHeaders headers, Role role, RoleFunction roleFunction)
    {
        try {
            return  roleService.editRole(userToken,headers,role,roleFunction);
        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        }

    }
}
