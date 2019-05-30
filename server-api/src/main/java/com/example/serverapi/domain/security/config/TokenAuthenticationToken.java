package com.example.serverapi.domain.security.config;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * token令牌类，用来包装参数token，传递给认证管理器
 *
 * @deprecated 可以直接使用UsernamePasswordAuthenticationToken的username字段来传递token
 * @author Ryan Miao
 * @date 2019/5/29 21:39
 * @see UsernamePasswordAuthenticationToken
 */
@Deprecated
public class TokenAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public TokenAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }
    public TokenAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }
}
