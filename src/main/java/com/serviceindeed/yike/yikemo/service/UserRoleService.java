package com.serviceindeed.yike.yikemo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.serviceindeed.yike.yikemo.config.CustomUser;
import com.serviceindeed.yike.yikemo.domain.User;
import com.serviceindeed.yike.yikemo.domain.UserRole;
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

@Service("userRoleService")
public class UserRoleService{
    @Autowired
    UserRoleMapper userRoleMapper;

    public List<UserRole> queryUserRoleByUserId(Long userId){
       return userRoleMapper.selectUserRoleByUserId(userId);
    }
}
