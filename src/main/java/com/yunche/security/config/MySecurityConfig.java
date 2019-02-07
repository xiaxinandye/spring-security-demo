package com.yunche.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName: MySecurityConfig
 * @Description:
 * @author: yunche
 * @date: 2019/02/07
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义Security 授权策略
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); //自定义规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1") //允许VIP角色1访问/level1/**请求
                .antMatchers("/level2/**").hasRole("VIP2") //允许VIP角色2访问/level2/**请求
                .antMatchers("/level3/**").hasRole("VIP3") //允许VIP角色3访问/level3/**请求
                .and().formLogin()  //开启基于登录表单的认证
        ;

        http.formLogin().loginPage("/userlogin");
        //开启记住我功能，如果能够接收到 name 为 remember 的表单元素
        http.rememberMe().rememberMeParameter("remember");
        //开启自动注销的功能，默认规则：通过发送 /logout请求，注销成功会返回 /login?logout 页面；来注销用户并清空session
        http.logout().logoutSuccessUrl("/"); //注销后来到首页
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.inMemoryAuthentication() // 添加基于内存的身份验证
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("zhangsan").password(new BCryptPasswordEncoder().encode("z123")).roles("VIP1") //将用户张三认证成VIP1角色
                .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("lisi").password(new BCryptPasswordEncoder().encode("l123")).roles("VIP2") //将用户李四认证成VIP2角色
                .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("wangwu").password(new BCryptPasswordEncoder().encode("w123")).roles("VIP3") //将用户王五认证成VIP3角色
        ;
    }
}
