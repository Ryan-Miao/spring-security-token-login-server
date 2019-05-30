package com.example.serverapi.domain.security.vo;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ryan Miao
 * @date 2019/5/30 13:48
 */
@Data
@Accessors(chain = true)
public class UserInfo {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobile;
    private Short enabled;
    private LocalDateTime lastPasswordResetDate;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    private Set<String> roles;
    private Set<String> permissions;

}
