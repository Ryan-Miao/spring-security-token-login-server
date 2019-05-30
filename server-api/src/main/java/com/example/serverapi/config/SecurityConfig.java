package com.example.serverapi.config;

import com.example.serverapi.domain.security.config.UserTokenAuthenticationProvider;
import com.example.serverapi.domain.security.config.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;

/**
 * @author Ryan Miao
 * @date 2019/5/29 19:54
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //DaoAuthenticationConfigurer-DaoAuthenticationProvider用来提供登录时用户名和密码认证
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //自定义TokenAuthenticationProvider, 用来提供token认证
        auth.authenticationProvider(new UserTokenAuthenticationProvider());

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

        http.cors()
            .and()
            .headers().frameOptions().disable()
            .and().csrf().disable()
            .authorizeRequests()
            //允许以下请求
//            .antMatchers("/v1/auth/**").permitAll()
            // 所有请求需要身份认证
            .anyRequest().authenticated()
            .and()
            //login   username,password通过UsernamePasswordAuthenticationFilter实现装载
            //token认证   需要对应的token解析，位于UsernamePasswordAuthenticationFilter之后
            .addFilterBefore(filter,
                BasicAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(new DigestAuthenticationEntryPoint())
            .accessDeniedHandler(new AccessDeniedHandlerImpl())
        ;
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

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
}
