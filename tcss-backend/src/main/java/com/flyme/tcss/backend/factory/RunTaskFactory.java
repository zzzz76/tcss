package com.flyme.tcss.backend.factory;

import com.flyme.tcss.backend.dao.TestCaseRepo;
import com.flyme.tcss.backend.domain.TestCase;
import com.flyme.tcss.backend.enums.CaseStatusEnum;
import com.flyme.tcss.backend.task.RunTask;
import com.flyme.tcss.backend.tools.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author xiaodao
 * @date 2022/12/26
 */
@Slf4j
@Component
public class RunTaskFactory {
    @Autowired
    private TestCaseRepo testCaseRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InstanceFactory instanceFactory;


    public RunTask buildRunTask(TestCase testCase) {
        return new TestCaseRunTask(testCase);
    }

    class TestCaseRunTask extends RunTask {

        private TestCase testCase;

        public TestCaseRunTask(TestCase testCase) {
            this.testCase = testCase;
        }

        @Override
        public void run() {
            String instanceName = instanceFactory.getInstance();
            if (instanceName != null && !instanceName.isEmpty()) {
                try {
                    CommonResult<?> result = restTemplate.postForObject("http://" + instanceName + "/submit", testCase, CommonResult.class);
                    if (result != null) {
                        TestCase resultCase = (TestCase) result.getData();
                        handle(resultCase);
                    } else {
                        log.error("post result is null");
                        onError();
                    }

                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                    onError();
                } finally {
                    instanceFactory.releaseInstance(instanceName);
                }

            } else {
                try {
                    Thread.sleep(3000);
                    execute();
                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                    onError();
                }
            }
        }

        private void handle(TestCase resultCase) {
            testCase.setCaseStatus(resultCase.getCaseType());
            testCase.setGmtModified(new Date());
            testCaseRepo.updateById(testCase);
        }

        private void onError() {
            testCase.setCaseStatus(CaseStatusEnum.EXCEPTION.getCode());
            testCase.setGmtModified(new Date());
            testCaseRepo.updateById(testCase);
        }

    }
}
