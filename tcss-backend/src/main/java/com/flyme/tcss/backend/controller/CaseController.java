package com.flyme.tcss.backend.controller;

import com.flyme.tcss.backend.dao.TestCaseRepo;
import com.flyme.tcss.backend.service.CaseService;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaodao
 * @date 2023/1/4
 */
@Slf4j
@RestController
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/test")
    public CommonResult test(@RequestParam(value = "count") Integer count) {
        List<TestCase> caseList = caseService.listTestCase();
        log.info("获取批量测试用例:{}，重复执行次数为:{}", caseList, count);
        for (int i = 0; i < count; i++) {
            for (TestCase testCase: caseList) {
                caseService.submitTestCase(testCase);
            }
        }
        return CommonResult.successResponse("提交成功");
    }
}
