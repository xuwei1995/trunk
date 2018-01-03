package com.serviceindeed.yike.yikemo.config;

import com.serviceindeed.yike.yikemo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


/**
 * UserDetailsService返回当前对象，该对象包含User实体，User必须实现Serializable
 * Author: 		Colin Feng
 * Created On: 	2017年12月15日
 */
public class CustomUser extends User implements UserDetails {
    private User user = null;

    public CustomUser(User user) {
        super(user);
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
