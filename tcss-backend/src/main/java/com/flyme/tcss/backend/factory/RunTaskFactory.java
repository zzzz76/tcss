package com.flyme.tcss.backend.factory;

import com.flyme.tcss.backend.dao.TestRecordRepo;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.common.domain.TestInstance;
import com.flyme.tcss.common.domain.TestRecord;
import com.flyme.tcss.common.enums.RecordStatusEnum;
import com.flyme.tcss.backend.task.RunTask;
import com.flyme.tcss.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaodao
 * @date 2022/12/26
 */
@Slf4j
@Component
public class RunTaskFactory {
    @Autowired
    private TestRecordRepo testRecordRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InstanceFactory instanceFactory;


    public RunTask buildRunTask(TestCase testCase, TestRecord testRecord) {
        return new TestCaseRunTask(testCase, testRecord);
    }

    class TestCaseRunTask extends RunTask {
        private final TestCase testCase;
        private final TestRecord testRecord;

        public TestCaseRunTask(TestCase testCase, TestRecord testRecord) {
            this.testCase = testCase;
            this.testRecord = testRecord;
        }

        @Override
        public void run() {
            TestInstance instance = instanceFactory.getInstance();
            if (instance != null) {
                try {
                    CommonResult result = restTemplate.postForObject("http://" + instance.getUrl() + "/submit", testCase, CommonResult.class);
                    if (result == null || !result.isSuccess()) {
                        log.error("请求测试服务实例失败，instanceName:{}, testCase:{}", instance, testCase);
                        onError();
                    }

                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                    onError();
                }
            }

            if (instance == null) {
                try {
                    Thread.sleep(3000);
                    submit();
                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                    onError();
                }
            }
        }

        private void onError() {
            testRecord.setStatus(RecordStatusEnum.EXCEPTION.getCode());
            testRecordRepo.updateById(testRecord);
        }

    }
}
