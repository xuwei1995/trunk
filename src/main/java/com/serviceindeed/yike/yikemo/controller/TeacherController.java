package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.domain.Teacher;
import com.serviceindeed.yike.yikemo.service.TeacherService;
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

/*gu add
*Author： gu
* create on： 2017-12-08
* */
@Controller
@RequestMapping("auth/teacher")
public class TeacherController  {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TeacherService teacherService;


    @RequestMapping("/getAllTeacher")
    @ResponseBody
    public Map<String, Object> getAllTeacher(HttpPages httpPages, Teacher teacher) {
        try {
            return     teacherService.queryPageTeacherList(teacher,httpPages)  ;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAllTeacher--查询数据失败:", e);
            if(!YiKeMoHelper.getInstance().isPaging(httpPages))
            {
                return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
            }
            return YiKeMoHelper.getInstance().getFailPageQueryJson( httpPages.getDraw());
        }

    }
    /**
     * 新增教师
     *@Author xw
    *@Date 2017/12/14 15:29
    */
   @RequestMapping("/addTeacher")
   @ResponseBody
   public  Map<String,Object> addTeacher(@RequestHeader HttpHeaders headers, @AuthenticationPrincipal User userToken, Teacher teacher){
       try{
           return teacherService.addTeacher(userToken,teacher,headers) ;
       }
       catch (Exception e)
       {
           log.error(e.getMessage(),e);
           return    YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
       }
   }
   /**
    * 编辑教师
    *@Author xw
    *@Date 2017/12/18 14:15
    */
   @RequestMapping("/editTeacher")
   @ResponseBody
   public  Map<String,Object> editTeacher(@RequestHeader HttpHeaders headers,Teacher teacher){
       try {
           return teacherService.editTeacher(teacher,headers);

       }catch (Exception e)//更新操作出错
       {
           log.error(e.getMessage(),e);
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//在catch中手动回滚
           if(e instanceof BadSqlGrammarException)
           {
               return  YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_003);
           }
           return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
       }
   }
}
