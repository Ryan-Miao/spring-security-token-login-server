package com.example.serverapi.log;

import java.util.Arrays;

/**
 * @author Ryan Miao
 */
@SuppressWarnings("PMD")
public enum SystemEvent {
    UN_KNOWN(100, "未知错误"),
    INTERNAL_SERVER_ERROR(500, "系统内部错误"),
    UN_AUTHORIZED_ERROR(401, "认证失败"),
    PERMISSION_NOT_ALLOWD_ERROR(403, "权限不足，不允许访问"),
    PARAM_INVALID_ERROR(400, "参数不符合条件"),

    ;
    private Integer code;
    private String description;

    SystemEvent(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SystemEvent findEvent(Integer code) {
        return Arrays.stream(SystemEvent.values()).filter(e -> e.getCode().equals(code)).findFirst()
            .orElse(UN_KNOWN);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name();
    }

    public String getDescription() {
        return this.description;
    }
}
