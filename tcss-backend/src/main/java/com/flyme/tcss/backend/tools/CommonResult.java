package com.flyme.tcss.backend.tools;

import lombok.Data;

/**
 * @author xiaodao
 * @date 2022/12/26
 */
@Data
public class CommonResult<T> {

    private final Integer status;

    private final T data;

    private final String msg;

    public static <T> CommonResult<T> successResponse(T data, String msg) {
        return new CommonResult<>(200, data, msg);
    }

    public static <T> CommonResult<T> successResponse(T data) {
        return new CommonResult<>(200, data, "success");
    }
}
