package com.flyme.tcss.tester.controller;

import com.flyme.tcss.common.dto.SubmissionDTO;
import com.flyme.tcss.common.result.CommonResult;
import com.flyme.tcss.tester.factory.RunTaskFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaodao
 * @date 2022/12/21
 */
@Slf4j
@RestController
public class TesterController {

    @Autowired
    private RunTaskFactory runTaskFactory;

    @PostMapping("/submit")
    public CommonResult submitCase(@RequestBody SubmissionDTO submission) {
        try {
            log.info("创建测试任务，并提交至线程池，submission:{}", submission);
            runTaskFactory.buildRunTask(submission).submit();
            return CommonResult.successResponse("提交成功");
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            return CommonResult.successResponse("提交失败");
        }
    }

    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.successResponse("提交成功");
    }

}
