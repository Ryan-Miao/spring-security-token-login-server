package com.example.serverapi.domain.security.service;

import com.example.serverapi.domain.security.vo.UserToken;

/**
 * @author Ryan Miao
 * @date 2019/5/30 13:42
 */
public interface IAuthService {


    UserToken doLogin(String username, String password);

}
