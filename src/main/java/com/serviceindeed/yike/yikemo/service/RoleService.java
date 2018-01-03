package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.domain.Role;
import com.serviceindeed.yike.yikemo.domain.RoleFunction;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.FunctionMapper;
import com.serviceindeed.yike.yikemo.mapper.RoleFunctionMapper;
import com.serviceindeed.yike.yikemo.mapper.RoleMapper;
import com.serviceindeed.yike.yikemo.util.Constant;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleFunctionMapper roleFunctionMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    //查询所有角色
    public Map getAllRoles(HttpPages httpPages,Role role) {
        if(YiKeMoHelper.getInstance().isPaging(httpPages))
        {//分页
            Page<Role> page= PageHelper.offsetPage(httpPages.getStart(),httpPages.getLength()).doSelectPage(()->
                    roleMapper.getAllRoles(role));
            return YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw()+1,page);
        }else {
            List<Role> roleList= roleMapper.getAllRoles(role) ;
            return YiKeMoHelper.getInstance().notPagingResult(roleList,roleList.size());
        }

    }
    //新增角色
    @Transactional
    public Map addRole(User userToken, HttpHeaders headers, Role role, RoleFunction roleFunction) {
            Date date=new Date();
            String userAgent=YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT);
            role.setRoleType(Constant.ROLE_TYPE_PT);//默认平台
            role.setCreatePlatform(userAgent);
            role.setCreateBy(userToken.getUserId());
            role.setCreateDate(date);
            roleMapper.insertSelective(role);
            if(roleFunction.getFunctionCodes().length>0)
            {
                roleFunction.setRoleId(role.getRoleId());
                roleFunction.setCreatePlatform(userAgent);
                roleFunction.setCreateBy(userToken.getUserId());
                roleFunction.setCreateDate(date);
                Arrays.asList(roleFunction.getFunctionCodes()).stream().forEach(code->{
                    roleFunction.setFunctionCode(code);
                    roleFunctionMapper.insert(roleFunction);
                });
                return   YiKeMoHelper.getInstance().successJsonResultMap(null,"新增角色成功");
            }else {
                return   YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_004);//functionCode为空
            }
    }
    //新增角色
    @Transactional
    public Map editRole(User userToken, HttpHeaders headers, Role role, RoleFunction roleFunction) {

                 YiKeMoHelper.getInstance().updateHelper(role,userToken.getUserId(),null,
                 YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT),null);
                 roleMapper.updateByPrimaryKeySelective(role);
                 YiKeMoHelper.getInstance().createHelper(roleFunction,userToken.getUserId(),null,
                 YiKeMoHelper.getInstance().getHttpHeaderInfo(headers,HttpHeaders.USER_AGENT),null);
                 if(roleFunction.getFunctionCodes().length>0)
                 {
                  roleFunctionMapper.deleteByRoleId(role.getRoleId()); //删除该角色下的所有权限
                  roleFunction.setRoleId(role.getRoleId());
                  Arrays.asList(roleFunction.getFunctionCodes()).stream().forEach(
                          code->
                          {
                              roleFunction.setFunctionCode(code);
                              roleFunctionMapper.insert(roleFunction); //遍历插入前台传入的新权限
                          });
            return   YiKeMoHelper.getInstance().successJsonResultMap(null,"修改角色成功");
        }else {
            return   YiKeMoHelper.getInstance().errorJsonResultMap(Constant.SERVER_MSG_004);//functionCode为空
        }
    }
}
