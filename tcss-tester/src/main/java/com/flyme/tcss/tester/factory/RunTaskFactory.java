package com.flyme.tcss.tester.factory;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.flyme.tcss.common.domain.TestInstance;
import com.flyme.tcss.common.domain.TestRecord;
import com.flyme.tcss.common.dto.SubmissionDTO;
import com.flyme.tcss.common.enums.RecordStatusEnum;
import com.flyme.tcss.tester.dao.TestInstanceRepo;
import com.flyme.tcss.tester.dao.TestRecordRepo;
import com.flyme.tcss.tester.task.RunTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiaodao
 * @date 2022/12/26
 */
@Slf4j
@Component
public class RunTaskFactory {
    @Resource
    private TestInstance initialInstance;

    @Autowired
    private TestRecordRepo testRecordRepo;

    @Autowired
    private TestInstanceRepo testInstanceRepo;

    public RunTask buildRunTask(SubmissionDTO submission) {
        return new TestCaseRunTask(submission);
    }

    class TestCaseRunTask extends RunTask {
        private final SubmissionDTO submission;

        public TestCaseRunTask(SubmissionDTO submission) {
            this.submission = submission;
        }

        @Override
        public void run() {
            TestRecord updateRecord = new TestRecord();
            updateRecord.setId(submission.getRecordId());
            try {
                String result = getTestResult(submission);
                boolean isSuccess = submission.getOutput().equals(result);
                RecordStatusEnum recordStatus = isSuccess ? RecordStatusEnum.SUCCEED : RecordStatusEnum.FAILED;

                updateRecord.setResult(result);
                updateRecord.setStatus(recordStatus.getCode());
                testRecordRepo.updateById(updateRecord);
                log.info("测试任务执行完毕，submission:{}, result:{}", submission, result);

            } catch (Throwable t) {
                updateRecord.setStatus(RecordStatusEnum.EXCEPTION.getCode());
                testRecordRepo.updateById(updateRecord);
                log.error(t.getMessage(), t);
            } finally {
                reduceCurrentTaskNum();
            }
        }

        @Override
        protected void beforeSubmit() {
            increaseCurrentTaskNum();
        }
    }

    private String getTestResult(SubmissionDTO submission) throws InterruptedException {
        Thread.sleep(3000);
        return submission.getInput();
    }

    private void increaseCurrentTaskNum() {
        boolean isOK = false;
        for (int i = 0; i < 10 && !isOK; i++) {
            TestInstance testInstance = testInstanceRepo.getById(initialInstance.getId());
            UpdateWrapper<TestInstance> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("task_num", testInstance.getTaskNum() + 1)
                    .set("version", testInstance.getVersion() + 1)
                    .eq("id", testInstance.getId())
                    .eq("version", testInstance.getVersion());

            isOK = testInstanceRepo.update(updateWrapper);
            if (!isOK) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void reduceCurrentTaskNum() {
        boolean isOK = false;
        for (int i = 0; i < 10 && !isOK; i++) {
            TestInstance testInstance = testInstanceRepo.getById(initialInstance.getId());
            UpdateWrapper<TestInstance> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("task_num", testInstance.getTaskNum() - 1)
                    .set("version", testInstance.getVersion() + 1)
                    .eq("id", testInstance.getId())
                    .eq("version", testInstance.getVersion());

            isOK = testInstanceRepo.update(updateWrapper);
            if (!isOK) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
