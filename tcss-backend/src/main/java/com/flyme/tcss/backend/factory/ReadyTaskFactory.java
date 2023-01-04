package com.flyme.tcss.backend.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flyme.tcss.backend.dao.TestRecordRepo;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.common.domain.TestRecord;
import com.flyme.tcss.common.enums.MutualTagEnum;
import com.flyme.tcss.common.enums.RecordStatusEnum;
import com.flyme.tcss.backend.task.ReadyTask;
import com.flyme.tcss.backend.task.RunTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaodao
 * @date 2022/12/27
 */
@Slf4j
@Component
public class ReadyTaskFactory {
    @Autowired
    private TestRecordRepo testRecordRepo;

    @Autowired
    private RunTaskFactory runTaskFactory;

    public ReadyTask buildReadyTask(TestCase testCase, TestRecord testRecord) {
        return new TestCaseReadyTask(testCase, testRecord);
    }

    class TestCaseReadyTask extends ReadyTask {
        private final TestCase testCase;
        private final TestRecord testRecord;

        public TestCaseReadyTask(TestCase testCase, TestRecord testRecord) {
            this.testCase = testCase;
            this.testRecord = testRecord;
        }

        @Override
        protected void run() {
            testRecord.setStatus(RecordStatusEnum.RUNNING.getCode());
            testRecordRepo.updateById(testRecord);

            log.info("创建运行任务，并提交至线程池，testCase:{}，testRecord:{}", testCase, testRecord);
            RunTask runTask = runTaskFactory.buildRunTask(testCase, testRecord);
            runTask.submit();
        }

        @Override
        protected boolean isReady() {
            return isPreReady() && isMutualReady();
        }

        @Override
        protected void onError() {
            testRecord.setStatus(RecordStatusEnum.EXCEPTION.getCode());
            testRecordRepo.updateById(testRecord);
        }

        private boolean isPreReady() {
            Long preId = testCase.getCasePre();
            if (preId == null) {
                return true;
            }

            QueryWrapper<TestRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("case_id", preId);

            List<String> statusList = new ArrayList<>();
            statusList.add(RecordStatusEnum.PENDING.getCode());
            statusList.add(RecordStatusEnum.RUNNING.getCode());
            queryWrapper.in("status", statusList);

            return testRecordRepo.count(queryWrapper) == 0;
        }

        private boolean isMutualReady() {
            String mutualTag = testCase.getMutualTag();
            if (mutualTag == null || mutualTag.isEmpty()) {
                return true;
            }

            QueryWrapper<TestRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("mutual_tag", mutualTag);
            queryWrapper.eq("status", RecordStatusEnum.RUNNING.getCode());
            return testRecordRepo.count(queryWrapper) == 0;
        }
    }


}
