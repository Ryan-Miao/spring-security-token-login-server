package com.example.serverapi.config;

import com.example.serverapi.domain.security.config.TokenAuthenticationFilter;
import com.example.serverapi.domain.security.config.UserTokenAuthenticationProvider;
import com.example.serverapi.domain.security.service.TokenManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;

/**
 * @author Ryan Miao
 * @date 2019/5/29 19:54
 */
@Configuration
@EnableWebSecurity
// 这里不启用方法aop权限校验，因为单独实现了GlobalMethodSecurityConfig
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenManagement tokenManagement;

    public SecurityConfig(TokenManagement tokenManagement) {
        this.tokenManagement = tokenManagement;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //DaoAuthenticationConfigurer-DaoAuthenticationProvider用来提供登录时用户名和密码认证
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //自定义TokenAuthenticationProvider, 用来提供token认证
        auth.authenticationProvider(new UserTokenAuthenticationProvider(tokenManagement));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] ignoreUrl = {"/v1/auth/**", "/", "/error", "/swagger-*/**", "/favicon.ico",
            "/webjars/**"};
        web.ignoring().antMatchers(
            ignoreUrl
        );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter("/**");
        filter.setAuthenticationManager(authenticationManagerBean());
        //如果本filter认证失败，是否继续认证，如果还有其他认证方式可以继续，
        //本demo只有token认证一种方案，认证失败即返回
        filter.setContinueChainBeforeSuccessfulAuthentication(false);
        filter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());

        //CHECKSTYLE:OFF
        http.cors()
            .and()
            .headers().frameOptions().disable()
            .and().csrf().disable()
            .authorizeRequests()
            //允许以下请求
            // 所有请求需要身份认证
            .anyRequest().authenticated()
            //.accessDecisionManager(accessDecisionManager())
            .and()
            //login   username,password通过UsernamePasswordAuthenticationFilter实现装载
            //token认证   需要对应的token解析，位于UsernamePasswordAuthenticationFilter之后
            .addFilterBefore(filter,
                BasicAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(new DigestAuthenticationEntryPoint())
            .accessDeniedHandler(new AccessDeniedHandlerImpl())
        ;
        //CHECKSTYLE:ON

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 移除权限的默认前缀ROLE_
     *
     * @return GrantedAuthorityDefaults
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    /**
     * 认证失败后跳转
     *
     * @return 认证失败后跳转
     */
    private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/v1/auth/error");
    }

//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//
//    }
}
