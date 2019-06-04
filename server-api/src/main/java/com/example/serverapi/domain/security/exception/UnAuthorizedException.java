package com.example.serverapi.domain.security.exception;

/**
 * 登录失败，返回401.
 * @author Ryan Miao
 * @date 2019/6/4 11:00
 */
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
