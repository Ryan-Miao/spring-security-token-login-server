package com.example.serverapi.domain.security.service.impl;

import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.service.IAuthService;
import com.example.serverapi.domain.security.service.IUserService;
import com.example.serverapi.domain.security.service.TokenManagement;
import com.example.serverapi.domain.security.vo.UserInfo;
import com.example.serverapi.domain.security.vo.UserToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Ryan Miao
 * @date 2019/5/30 13:46
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenManagement tokenManagement;


    public AuthServiceImpl(IUserService userService,
        PasswordEncoder passwordEncoder,
        TokenManagement tokenManagement) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenManagement = tokenManagement;
    }

    @Override
    public UserToken doLogin(String username, String password) {
        User userInfo = userService.findSecurityUserByUsername(username);
        if (userInfo == null) {
            throw new BadCredentialsException("用户名或密码不正确");
        }

        String encodedPassword = userInfo.getPassword();

        boolean matches = passwordEncoder.matches(password, encodedPassword);

        if (matches){
            userInfo.setPassword("******");
            return tokenManagement.save(userInfo);
        }

        throw new BadCredentialsException("用户名或密码不正确");
    }

}
