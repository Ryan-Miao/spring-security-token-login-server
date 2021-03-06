package com.example.serverapi.domain.security.controller;

import com.example.serverapi.domain.common.vo.BaseResponse;
import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Miao
 * @date 2019/5/30 11:37
 */
@RestController
@RequestMapping("/v1/users")

public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('can_list_user')")
    public BaseResponse<List<User>> list() {
        List<User> users = userMapper.selectAll();
        return new BaseResponse<>(users);
    }

    @ApiOperation(value = "current", notes = "获取当前用户，用来校验登录，不用传参，token从cookie中读取")
    @GetMapping("current")
    public BaseResponse<User> currentUser(
        @ApiParam(hidden = true) @AuthenticationPrincipal Authentication authentication) {

        Object credentials = authentication.getCredentials();
        System.out.println(credentials);

        User details = (User) authentication.getDetails();
        return new BaseResponse<>(details);
    }

    @PreAuthorize("hasAnyAuthority('test')")
    @GetMapping("test")
    public String test() {

        return "test";
    }

    @PreAuthorize("hasAnyAuthority('ryan')")
    @GetMapping("ryan")
    public String ryan() {

        return "ryan";
    }

}
