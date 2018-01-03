package com.serviceindeed.yike.yikemo.controller;

import com.serviceindeed.yike.yikemo.domain.Function;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.UserFunction;
import com.serviceindeed.yike.yikemo.service.FunctionService;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.MyException;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth/function")
public class FunctionController {
    Logger log= LoggerFactory.getLogger(this.getClass());
    @Autowired
    FunctionService functionService;

    @RequestMapping("/getAllFunctions")
    @ResponseBody
    public Map getAllFunctions(){
    return functionService.getAllFunctions();
    }

    @RequestMapping("/getFunctionsByRoleId")
    @ResponseBody
    public Map getFunctionsByRoleId(@RequestParam("roleId") Long roleId){
        return functionService.getFunctionsByRoleId(roleId);
    }

    @RequestMapping("/getFunctionsByUserId")
    @ResponseBody
    public Map getFunctionsByUserId(@RequestParam("userId") Long userId){
        try {
            return functionService.getFunctionsByUserId(userId);

        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
   /* @RequestMapping("/addUserFunction")
    @ResponseBody
    public Map addUserFunction(@AuthenticationPrincipal User userToken,User user, UserFunction userFunction, @RequestHeader HttpHeaders headers){
        try {
            functionService.addUserFunction(userToken,userFunction,user,headers);
          return YiKeMoHelper.getInstance().successJsonResultMap(null,"新增成功");

        }
        catch (MyException e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }*/
    @RequestMapping("editUserFunctionByUserId")
    @ResponseBody
    public Map editUserFunctionByUserId(@AuthenticationPrincipal User userToken,User user, UserFunction userFunction, @RequestHeader HttpHeaders headers){
        try {
            functionService.editUserFunctionByUserId(userToken,userFunction,user,headers);
            return YiKeMoHelper.getInstance().successJsonResultMap(null,"修改成功");

        }
        catch (MyException e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }
    /*@RequestMapping("/getUserFunctionByUserId")
    @ResponseBody
    public Map addUserFunction(@RequestParam("userId")Long userId){
        try {
             List<UserFunction> userFunctionList= functionService.getUserFunctionByUserId(userId);
            return YiKeMoHelper.getInstance().successJsonResultMap(userFunctionList,Constant.QUERY_SUCCESS);

        }
        catch (MyException e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(e.getMessage());
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            return YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_001);
        }
    }*/
}
