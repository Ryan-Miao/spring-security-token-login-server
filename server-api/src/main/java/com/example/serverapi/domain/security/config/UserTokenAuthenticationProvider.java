package com.example.serverapi.domain.security.config;

import com.example.serverapi.ServerApiApplication;
import com.example.serverapi.domain.security.entity.Role;
import com.example.serverapi.domain.security.service.TokenManagement;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 这里只使用了username字段。
 *
 * @author Ryan Miao
 * @date 2019/5/29 22:05
 */
public class UserTokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
        UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }


    @Override
    protected UserDetails retrieveUser(String token,
        UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //验证token
        TokenManagement tokenManagement = ServerApiApplication.context
            .getBean(TokenManagement.class);
        com.example.serverapi.domain.security.entity.User userInfo = tokenManagement.get(token);

        if (userInfo == null) {
            throw new BadCredentialsException("token认证失败");
        }

        authentication.setDetails(userInfo);

        Set<SimpleGrantedAuthority> authorities = userInfo.getRoleList().stream()
            .map(Role::getPermissionList)
            .flatMap(Collection::stream)
            .map(p -> new SimpleGrantedAuthority(p.getName())).collect(
                Collectors.toSet());
        return new User(userInfo.getUsername(), userInfo.getPassword(), authorities);
    }

    /**
     * 对应我们的Token令牌类UsernamePasswordAuthenticationToken，可以采用本provide验证.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
            .isAssignableFrom(authentication));
    }
}
