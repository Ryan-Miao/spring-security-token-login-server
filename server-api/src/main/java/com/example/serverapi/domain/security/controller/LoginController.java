package com.example.serverapi.domain.security.controller;

import com.example.serverapi.domain.common.vo.BaseResponse;
import com.example.serverapi.domain.security.service.IAuthService;
import com.example.serverapi.domain.security.utils.TokenUtils;
import com.example.serverapi.domain.security.vo.UserToken;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletResponse;
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

    private final IAuthService authService;

    public LoginController(
        IAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("login")
    public BaseResponse<UserToken> login(
        @ApiParam("username") @RequestParam String username,
        @ApiParam("password sha1后的值") @RequestParam String password,
        HttpServletResponse response
    ) {
        UserToken token = authService.doLogin(username, password);

        TokenUtils.writeTokenToCookie(response, token.getToken());

        return new BaseResponse<>(token);

    }

}
