package com.example.serverapi.domain.common.vo;

import lombok.Data;

/**
 * @author Ryan Miao
 * @date 2019/5/29 22:46
 */
@Data
public class BaseResponse<T> {

    public static int SUCCESS = 0;

    /**
     * 返回数据
     */
    private T data;

    private String msg;
    /**
     * 状态码
     */
    private int code;

    public BaseResponse(T data) {
        this.data = data;
        this.code = SUCCESS;
    }

    public BaseResponse(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
