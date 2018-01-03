package com.serviceindeed.yike.yikemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 权限相关配置：注册密码算法，设置UserDetailsService
 * Author: 		Colin Feng
 * Created On: 	2017年12月15日
 * Ref:
 * https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/cors.html
 * https://stackoverflow.com/questions/40418441/spring-security-cors-filter
 * https://stackoverflow.com/questions/21783079/ajax-in-chrome-sending-options-instead-of-get-post-put-delete
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    public static void main(String[] args) {


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/auth/*").authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 允许OPTIONS方法访问，不做auth验证，因为的跨域开发是，Chrome浏览器会先发送OPTIONS在真正执行GET POST
        // 如果正式发布时在同一个域下，请去掉`and().ignoring().antMatchers(HttpMethod.OPTIONS)`
        web.ignoring().antMatchers("/anon/**").and().ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
