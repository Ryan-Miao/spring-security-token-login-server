package com.example.serverapi.domain.security.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * @author Ryan Miao
 * @date 2019/5/29 18:06
 */
@Data
public class Role {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    private List<Permission> permissionList;

}
