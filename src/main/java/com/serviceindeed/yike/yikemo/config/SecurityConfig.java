//package com.serviceindeed.yike.yikemo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * Author:  Colin.Feng
// * Created On: 2017/6/28
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf();
////        http.authorizeRequests()
////                .and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/", true)
////                .and().logout().logoutUrl("/logout")
////                .and().sessionManagement().maximumSessions(1).expiredUrl("/expired")
////                .and()
////                .and().exceptionHandling().accessDeniedPage("/accessDenied");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/js/**", "/css/**", "/lib/**", "/font/**", "/images/**", "/**/favicon.ico");
//    }
//
//
//}
