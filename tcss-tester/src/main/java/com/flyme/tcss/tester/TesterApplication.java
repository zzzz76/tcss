package com.flyme.tcss.tester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiaodao
 * @date 2022/12/21
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TesterApplication {
    public static void main(String[] args) {
        SpringApplication.run(TesterApplication.class, args);
    }
}
