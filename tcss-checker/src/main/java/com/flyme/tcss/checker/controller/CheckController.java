package com.flyme.tcss.checker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaodao
 * @date 2022/12/21
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    // 服务调用凭证
    @Value("${tcss.check.token}")
    private String checkToken;

    @GetMapping("/submit")
    public String submitCase() {
        System.out.println("submit case:" + checkToken);
        return "{'submit': 'case'}";
    }
}
