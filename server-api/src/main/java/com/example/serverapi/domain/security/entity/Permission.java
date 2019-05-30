package com.example.serverapi.domain.security.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author Ryan Miao
 * @date 2019/5/29 18:06
 */
@Data
public class Permission {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
