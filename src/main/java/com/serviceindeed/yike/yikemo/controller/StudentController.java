package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.domain.Student;
import com.serviceindeed.yike.yikemo.service.GeneralService;
import com.serviceindeed.yike.yikemo.service.OrganizationService;
import com.serviceindeed.yike.yikemo.service.StudentService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
/**
 * sun add
 * Author:sun fei
 * create on:2017-12-04
 */
@Controller
@RequestMapping("auth/")
public class StudentController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StudentService studentService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private GeneralService generalService;

    /**
     * 分页查询学生信息
     * @param httpPages
     * @param student
     * @return
     */
    @RequestMapping("student/getAllStudent")
    @ResponseBody
    public Map<String, Object> getAllStudent(@AuthenticationPrincipal User userToken, HttpPages httpPages, Student student) {
        try{
            return  studentService.queryPageStudentList(student,httpPages);

        }catch (Exception e)
        {
            e.printStackTrace();
            log.error("getAllStudent--查询学生信息失败:", e);
            if(e instanceof BadSqlGrammarException)
            {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
            }
            return    YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }
    /**
     * 新增学生用户
     *@Author xw
     *@Date 2017/12/14 15:47
     */
    @RequestMapping("student/addStudent")
    @ResponseBody
    public Map<String,Object> addStudent(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, Student student){
        try{
            return   studentService.addStudent(userToken,student,headers);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            return   YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }
    /**
     * 修改学生
     *@Author xw
     *@Date 2017/12/15 17:46
     */
    @RequestMapping("student/editStudent")
    @ResponseBody
    public Map<String,Object> editStudent(@RequestHeader HttpHeaders headers,@AuthenticationPrincipal User userToken,Student student){
        try {
            return    studentService.editStudent(userToken,student,headers);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改学生用户失败", e);
            return YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }
    /**
     * 修改学生密码
     *@Author xw
     *@Date 2017/12/22 14:35
     */
    @RequestMapping("user/editUserPwd")
    @ResponseBody
    public Map<String,Object> editStudent(@RequestHeader HttpHeaders headers,@AuthenticationPrincipal User userToken,User user){
        try {
            return    studentService.editStudentPwd(userToken,user,headers);

        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }
    /**
     * 批量充值
     *@Author xw
     *@Date 2017/12/22 15:22
     */
    @RequestMapping("student/batchRecharge")
    @ResponseBody
    public Map<String,Object> batchRecharge(@RequestHeader HttpHeaders headers,@AuthenticationPrincipal User userToken,Student student){
        try {
            return    studentService.batchRecharge(userToken,student,headers);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//在catch中手动回滚
            return  YiKeMoHelper.getInstance().serverExceptionMap;
        }
    }
}
