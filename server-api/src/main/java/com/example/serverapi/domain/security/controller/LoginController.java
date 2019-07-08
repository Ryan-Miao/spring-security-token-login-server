package com.example.serverapi.domain.security.controller;

import com.example.serverapi.domain.common.vo.BaseResponse;
import com.example.serverapi.domain.security.exception.UnAuthorizedException;
import com.example.serverapi.domain.security.service.IAuthService;
import com.example.serverapi.domain.security.utils.TokenUtils;
import com.example.serverapi.domain.security.vo.UserToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    @ApiOperation(value = "登录", notes = "password应该在客户端sha1加密之后，传输过来。调试可以设置encoded来测试")
    @GetMapping("login")
    public BaseResponse<UserToken> login(
            @ApiParam("username") @RequestParam String username,
            @ApiParam("password sha1后的值") @RequestParam String password,
            @ApiParam(value = "是否sha1, 是则密码sha1后传输，否则原密码传输") @RequestParam(required = false, defaultValue = "true") boolean encoded,
            HttpServletResponse response
    ) {

        if (!encoded) {
            password = DigestUtils.sha1Hex(password);
        }

        UserToken token = authService.doLogin(username, password);

        TokenUtils.writeTokenToCookie(response, token.getToken());

        return new BaseResponse<>(token);

    }

    @GetMapping("error")
    public BaseResponse<String> error() {
        throw new UnAuthorizedException("登录失效");
    }

    public static void main(String[] args) {
        printEncodedPass("ryan");
        printEncodedPass("admin");
        printEncodedPass("test");
    }

    private static void printEncodedPass(String original) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String sha1Hex = DigestUtils.sha1Hex(original);
        String encoded = encoder.encode(sha1Hex);

        System.out.println("original: " + original);
        System.out.println("sha1: " + sha1Hex);
        System.out.println("encoded: " + encoded);

    }


}
