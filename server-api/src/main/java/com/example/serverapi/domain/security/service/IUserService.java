package com.example.serverapi.domain.security.service;

import com.example.serverapi.domain.security.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Ryan Miao
 * @date 2019/5/29 20:23
 */
public interface IUserService {


    User getById();

    /**
     * @param username 用户名
     * @return 用户的基本信息，role， permission.
     */
    User findSecurityUserByUsername(String username) throws UsernameNotFoundException;

}
