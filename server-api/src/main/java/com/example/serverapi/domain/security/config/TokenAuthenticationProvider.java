package com.example.serverapi.domain.security.config;

import com.example.serverapi.ServerApiApplication;
import com.example.serverapi.domain.security.entity.Role;
import com.example.serverapi.domain.security.service.TokenManagement;
import com.example.serverapi.domain.security.vo.UserInfo;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * @deprecated 采用集成AbstractAuthenticationProcessingFilter的方式UserTokenAuthenticationProvider
 * @author Ryan Miao
 * @date 2019/5/29 22:05
 */
@Deprecated
public class TokenAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        Assert.isInstanceOf(TokenAuthenticationToken.class, authentication,
            "只支持TokenAuthenticationToken认证");

        if (authentication.getPrincipal() == null) {
            throw new UsernameNotFoundException("token不能为空");
        }
        String token = authentication.getPrincipal().toString();

        //验证token
        TokenManagement tokenManagement = ServerApiApplication.context
            .getBean(TokenManagement.class);
        com.example.serverapi.domain.security.entity.User userInfo = tokenManagement.get(token);

        if (userInfo == null) {
            throw new BadCredentialsException("token认证失败");
        }

        Set<SimpleGrantedAuthority> authorities = userInfo.getRoleList().stream()
            .map(Role::getPermissionList)
            .flatMap(Collection::stream)
            .map(p -> new SimpleGrantedAuthority(p.getName())).collect(
                Collectors.toSet());

        TokenAuthenticationToken tokenAuthenticationToken = new TokenAuthenticationToken(token,
            authorities);
        tokenAuthenticationToken.setDetails(userInfo);
        return tokenAuthenticationToken;
    }

    /**
     * 对应我们的Token令牌类TokenAuthenticationToken，可以采用本provide验证.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (TokenAuthenticationToken.class
            .isAssignableFrom(authentication));
    }
}
