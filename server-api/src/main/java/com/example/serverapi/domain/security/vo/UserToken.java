package com.example.serverapi.domain.security.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ryan Miao
 * @date 2019/5/29 22:49
 */
@Data
@Accessors(chain = true)
public class UserToken {
    private String token;
    private long expireInSecond;

}
