package com.example.serverapi.domain.security.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Ryan Miao
 * @date 2019/5/29 17:37
 */
@Data
@Table(name = "\"public\".\"user\"")
public class User {

    @Id
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

    private List<Role> roleList;

}
