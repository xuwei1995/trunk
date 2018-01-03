package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.config.CustomUser;
import com.serviceindeed.yike.yikemo.domain.*;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.domain.model.UserFunctionList;
import com.serviceindeed.yike.yikemo.domain.model.UserInfo;
import com.serviceindeed.yike.yikemo.service.*;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.MyException;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("auth/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    FunctionService functionService;
    @Autowired
    StudentService studentService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/getAllUsers")
    @ResponseBody
    public Map getAllUsers(HttpPages httpPages, User user) {
        try {
            return userService.getAllUsers(httpPages, user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }

    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Map addUser(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, User user) {
        try {
            return studentService.addUser(headers, userToken, user);
        } catch (MyException e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//try中需要手动回滚
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }

    @RequestMapping("/editUser")
    @ResponseBody
    public Map editUser(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, User user) {
        try {
            return studentService.editUser(headers, userToken, user);
        } catch (MyException e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().serverExceptionMap;
        }

    }

    /**
     * 根据token获取用户信息
     *
     * @param auth
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map<String, Object> getUserInfo(OAuth2Authentication auth) {
        try {
            CustomUser customUser = (CustomUser) auth.getPrincipal();
            Long userId = customUser.getUserId();
            UserInfo userInfo;
            //返回的结果集
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //存放该用户主功能集合
            List<UserFunctionList> functionTotalList = new ArrayList<UserFunctionList>();
            if (userId != null) {
                //根据用户ID对应的用户信息
                User user = userService.queryUserById(userId);
                //判空
                if (user != null) {
                    //判断用户类型
                    //如果是教师,则查询教师视图
                    if (user.getUserType().equals("OG") || user.getUserType().equals("SH")) {
                        userInfo = teacherService.queryTeacherById(userId);
                        resultMap.put("userInfo", userInfo);
                        //根据用户Id查询用户所属角色
                        List<UserRole> userRoleList = userRoleService.queryUserRoleByUserId(userId);
                        //存放该用户已有功能集合
                        HashMap<String, UserFunctionList> functionMap = new HashMap<String, UserFunctionList>();
                        //遍历用户角色
                        for (UserRole userRole : userRoleList) {
                            //根据角色Id查询角色功能信息
                            List<UserFunctionList> functionList = functionService.getFunctionListByRoleId(userRole.getRoleId());
                            for (UserFunctionList function : functionList) {
                                //去重复功能
                                if (functionMap.containsKey(function.getFunctionCode()) == false) {
                                    functionMap.put(function.getFunctionCode(), function);
                                    functionTotalList.add(function);
                                }
                            }
                        }
                    }
                    //如果是平台，则查询平台视图
                    else if (user.getUserType().equals("PT")) {
                       userInfo = userService.queryPlatformUserById(userId);
                        resultMap.put("userInfo", userInfo);
                        //根据用户Id查询用户所有权限
                        functionTotalList = functionService.getFunctionListByUserId(userId);
                    }
                    //如果是学生
                    else if (user.getUserType().equals("ST")) {
                        userInfo = studentService.queryStudentById(userId);
                        resultMap.put("userInfo", userInfo);
                    }
                    resultMap.put("userFunctions", functionTotalList);
                    return YiKeMoHelper.getInstance().successJsonResultMap(resultMap, Constant.QUERY_SUCCESS);
                } else {
                    return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.USER_MSG_001);
                }
            } else {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.USER_MSG_002);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }

}
