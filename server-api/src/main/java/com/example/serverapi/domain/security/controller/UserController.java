package com.example.serverapi.domain.security.controller;

import com.example.serverapi.domain.common.vo.BaseResponse;
import com.example.serverapi.domain.security.entity.User;
import com.example.serverapi.domain.security.mapper.UserMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @GetMapping("current")
    public BaseResponse<User> currentUser(Authentication authentication) {

        Object credentials = authentication.getCredentials();
        System.out.println(credentials);

        User details = (User) authentication.getDetails();
        return new BaseResponse<>(details);
    }

}
