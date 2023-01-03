package com.flyme.tcss.backend.service.impl;

import com.flyme.tcss.backend.dao.TestCaseRepo;
import com.flyme.tcss.backend.dao.TestRecordRepo;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.common.domain.TestRecord;
import com.flyme.tcss.common.enums.RecordStatusEnum;
import com.flyme.tcss.backend.factory.ReadyTaskFactory;
import com.flyme.tcss.backend.service.CaseService;
import com.flyme.tcss.backend.task.ReadyTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
@Slf4j
@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    private ReadyTaskFactory readyTaskFactory;

    @Autowired
    private TestCaseRepo testCaseRepo;

    @Autowired
    private TestRecordRepo testRecordRepo;

    @Override
    public void submitTestCase(TestCase testCase) {
        // 每一次提交测试用例，都会生成测试记录
        TestRecord testRecord = new TestRecord();
        testRecord.setCaseId(testCase.getId());
        testRecord.setStatus(RecordStatusEnum.PENDING.getCode());
        testRecordRepo.save(testRecord);
        // 创建就绪任务，并提交至就绪队列
        ReadyTask readyTask = readyTaskFactory.buildReadyTask(testCase, testRecord);
        readyTask.submit();
    }

    @Override
    public TestCase getTestCase(Long id) {
        return testCaseRepo.getById(id);
    }
}
