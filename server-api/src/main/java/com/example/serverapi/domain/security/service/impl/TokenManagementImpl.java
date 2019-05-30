package com.example.serverapi.domain.security.service.impl;

import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.service.TokenManagement;
import com.example.serverapi.domain.security.vo.UserToken;
import java.util.UUID;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

/**
 * @author Ryan Miao
 * @date 2019/5/30 14:08
 */
@Service
public class TokenManagementImpl implements TokenManagement {

    private final RedissonSpringCacheManager cacheManager;

    public TokenManagementImpl(RedissonSpringCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public UserToken save(User userInfo) {

        //generate token
        String token = UUID.randomUUID().toString();
        Long userId = userInfo.getId();

        Cache tokenCache = getTokenCache();
        Cache userIdCache = getUserIdCache();

        assert tokenCache != null;
        assert userIdCache != null;
        tokenCache.put(token, userInfo);
        userIdCache.put(userId, token);
        return new UserToken().setToken(token).setExpireInSecond(10);
    }


    @Override
    public User get(String token) {
        Cache cache = getTokenCache();
        return cache.get(token, User.class);
    }


    private Cache getUserIdCache() {
        return cacheManager.getCache("auth_server_user");
    }

    private Cache getTokenCache() {
        return cacheManager.getCache("auth_server_token");
    }
}
