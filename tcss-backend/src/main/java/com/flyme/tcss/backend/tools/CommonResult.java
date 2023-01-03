package com.flyme.tcss.backend.tools;

import lombok.Data;

/**
 * @author xiaodao
 * @date 2022/12/26
 */
@Data
public class CommonResult {
    private boolean success;
    private String message;

    public CommonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static CommonResult successResponse(String message) {
        return new CommonResult(true, message);
    }

    public static CommonResult failResponse(String message) {
        return new CommonResult(false, message);
    }
}
