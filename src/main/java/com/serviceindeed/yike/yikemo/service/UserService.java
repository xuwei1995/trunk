package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.config.CustomUser;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.model.UserInfo;
import com.serviceindeed.yike.yikemo.domain.helper.HttpPages;
import com.serviceindeed.yike.yikemo.mapper.StudentMapper;
import com.serviceindeed.yike.yikemo.mapper.UserMapper;
import com.serviceindeed.yike.yikemo.mapper.UserRoleMapper;
import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userDetailsService")
public class UserService implements UserDetailsService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userMapper.selectByIfkCode(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        return new CustomUser(user);
    }

    public Map getAllUsers(HttpPages httpPages,User user) {
        if(YiKeMoHelper.getInstance().isPaging(httpPages))//分页
        {
            Page<User> page= PageHelper.offsetPage(httpPages.getStart(),httpPages.getLength()).doSelectPage(()->userMapper.getAllUsers(user));
            return  YiKeMoHelper.getInstance().getSuccessPageQueryJson(httpPages.getDraw()+1,page);
        }else { //不分页
           List<User> userList= userMapper.getAllUsers(user);
            return  YiKeMoHelper.getInstance().notPagingResult(userList,userList.size());
        }
    }

    /**
     * 根据用户Id查询用户信息
     * @param userId
     * @return
     */
    public User queryUserById(Long userId) {
       return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据平台用户Id查询平台用户视图，返回平台用户信息
     * @param userId
     * @return
     */
    public UserInfo queryPlatformUserById(Long userId) {
        return userMapper.queryPlatformUserInfo(userId);
    }
}
