package com.flyme.tcss.checker.controller;

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

    @GetMapping("/submit")
    public String submitCase() {
        System.out.println("submit case:");
        return "{'submit': 'case'}";
    }
}
