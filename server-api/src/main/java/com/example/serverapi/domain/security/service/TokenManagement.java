package com.example.serverapi.domain.security.service;

import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.vo.UserToken;

/**
 * @author Ryan Miao
 * @date 2019/5/30 14:07
 */
public interface TokenManagement {

    UserToken save(User userInfo);

    User get(String token);

}
