package com.example.serverapi.domain.security.controller;

import com.example.serverapi.domain.common.vo.BaseResponse;
import com.example.serverapi.domain.security.service.IAuthService;
import com.example.serverapi.domain.security.utils.TokenUtils;
import com.example.serverapi.domain.security.vo.UserToken;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletResponse;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Miao
 * @date 2019/5/29 22:44
 */
@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    private final RedissonClient redissonClient;
    private final IAuthService authService;

    public LoginController(RedissonClient redissonClient,
        IAuthService authService) {
        this.redissonClient = redissonClient;
        this.authService = authService;
    }

    @GetMapping("login")
    public BaseResponse<UserToken> login(
        @ApiParam("username") @RequestParam String username,
        @ApiParam("password sha1后的值") @RequestParam String password,
        HttpServletResponse response
    ) {

        RMapCache<Object, Object> cache = redissonClient
            .getMapCache("auth_server_token");
        cache.put(username, password);

        UserToken token = authService.doLogin(username, password);

        TokenUtils.writeTokenToCookie(response, token.getToken());

        return new BaseResponse<>(token);

    }

}
