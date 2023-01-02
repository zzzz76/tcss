package com.flyme.tcss.backend.factory;

import com.flyme.tcss.backend.dao.TestCaseRepo;
import com.flyme.tcss.backend.domain.TestCase;
import com.flyme.tcss.backend.enums.CaseStatusEnum;
import com.flyme.tcss.backend.task.ReadyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author xiaodao
 * @date 2022/12/27
 */
@Component
public class ReadyTaskFactory {
    @Autowired
    private TestCaseRepo testCaseRepo;

    @Autowired
    private RunTaskFactory runTaskFactory;

    public ReadyTask buildReadyTask(TestCase testCase) {
        return new TestCaseReadyTask(testCase);
    }

    class TestCaseReadyTask extends ReadyTask {
        private TestCase testCase;

        public TestCaseReadyTask(TestCase testCase) {
            this.testCase = testCase;
        }

        @Override
        protected void run() {
            runTaskFactory.buildRunTask(testCase).execute();
        }

        @Override
        protected boolean isReady() {
            TestCase preCase = testCaseRepo.getById(testCase.getCasePre());
            CaseStatusEnum caseStatus = CaseStatusEnum.getItem(preCase.getCaseStatus());
            return caseStatus == CaseStatusEnum.SUCCEED;
        }

        @Override
        protected void onError() {
            testCase.setCaseStatus(CaseStatusEnum.EXCEPTION.getCode());
            testCase.setGmtModified(new Date());
            testCaseRepo.updateById(testCase);
        }
    }
}
