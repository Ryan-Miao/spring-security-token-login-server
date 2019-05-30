package com.example.serverapi.domain.security.mapper;

import com.example.serverapi.domain.security.entity.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Ryan Miao
 * @date 2019/5/29 18:08
 */
@Component
public interface UserMapper extends Mapper<User> {

    org.springframework.security.core.userdetails.User findByUserName(String username);
}
