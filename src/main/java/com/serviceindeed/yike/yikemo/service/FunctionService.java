package com.serviceindeed.yike.yikemo.service;

import com.serviceindeed.yike.yikemo.domain.Function;
import com.serviceindeed.yike.yikemo.domain.RoleFunction;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.UserFunction;
import com.serviceindeed.yike.yikemo.domain.model.UserFunctionList;
import com.serviceindeed.yike.yikemo.mapper.FunctionMapper;
import com.serviceindeed.yike.yikemo.mapper.RoleFunctionMapper;
import com.serviceindeed.yike.yikemo.mapper.UserFunctionMapper;
import com.serviceindeed.yike.yikemo.mapper.UserMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.MyException;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FunctionService {
    @Autowired
    FunctionMapper functionMapper;
    @Autowired
    RoleFunctionMapper roleFunctionMapper;
    @Autowired
    UserFunctionMapper userFunctionMapper;
    @Autowired
    UserMapper userMapper;

    public Map getAllFunctions() {
         List<Function> functionList= functionMapper.getAllFunctions();
        return YiKeMoHelper.getInstance().notPagingResult(functionList,functionList.size());
    }

    public Map getFunctionsByRoleId(Long roleId) {
        List<RoleFunction> roleFunctionList= roleFunctionMapper.getFunctionsByRoleId(roleId);
        return YiKeMoHelper.getInstance().notPagingResult(roleFunctionList,roleFunctionList.size());
    }

    public Map getFunctionsByUserId(Long userId) {
        List<UserFunction> userFunctionList= userFunctionMapper.getFunctionsByUserId(userId);
        return YiKeMoHelper.getInstance().notPagingResult(userFunctionList,userFunctionList.size());
    }

    /**
     * 根据角色Id查询角色对应功能列表
     * @param roleId
     * @return
     */
    public List<UserFunctionList> getFunctionListByRoleId(Long roleId) {
        return functionMapper.selectFunctionsByRoleId(roleId);
    }
    /**
     * 根据用户Id查询用户对应功能列表
     * @param userId
     * @return
     */
    public List<UserFunctionList> getFunctionListByUserId(Long userId) {
       return functionMapper.selectFunctionsByUserId(userId);
    }
    //新增userfunctions
  /*  @Transactional
    public void addUserFunction(User userToken, UserFunction userFunction,User user, HttpHeaders headers) {
          String userAgent= YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT);
          YiKeMoHelper.getInstance().updateHelper(user,userToken.getUserId(),null,userAgent,null);
          userMapper.updateByPrimaryKeySelective(user);
          YiKeMoHelper.getInstance().createHelper(userFunction,userToken.getUserId(),null,userAgent,null);
          String [] functions=userFunction.getFunctionCodes();
           if(functions.length>0)
           {
               Arrays.stream(functions).forEach(function->{
                   userFunction.setFunctionCode(function);
                   userFunctionMapper.insertSelective(userFunction);
               });
           }else {
               throw  new MyException(Constant.SERVER_MSG_002);
           }
    }*/
    //修改用户权限表
    @Transactional
    public void editUserFunctionByUserId(User userToken, UserFunction userFunction, User user,HttpHeaders headers) {
        String userAgent= YiKeMoHelper.getInstance().getHttpHeaderInfo(headers, headers.USER_AGENT);
        YiKeMoHelper.getInstance().updateHelper(user,userToken.getUserId(),null,userAgent,null);
        userMapper.updateByPrimaryKeySelective(user);
        userFunctionMapper.deleteByUserId(user.getUserId());
        YiKeMoHelper.getInstance().createHelper(userFunction,userToken.getUserId(),null,userAgent,null);
        String [] functions=userFunction.getFunctionCodes();
        if(functions.length>0)
        {
            Arrays.stream(functions).forEach(function->{
                userFunction.setFunctionCode(function);
                userFunctionMapper.insertSelective(userFunction);
            });
        }else {
            throw  new MyException(Constant.SERVER_MSG_002);
        }
    }

   /* public List<UserFunction> getUserFunctionByUserId(Long userId) {
        return  userFunctionMapper.getUserFunctionByUserId(userId);
    }*/
}
