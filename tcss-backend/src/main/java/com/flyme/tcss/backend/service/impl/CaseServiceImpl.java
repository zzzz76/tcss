package com.flyme.tcss.backend.service.impl;

import com.flyme.tcss.backend.domain.TestCase;
import com.flyme.tcss.backend.factory.ReadyTaskFactory;
import com.flyme.tcss.backend.service.CaseService;
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

    @Override
    public void submitTestCase(TestCase testCase) {
        readyTaskFactory.buildReadyTask(testCase).execute();
    }
}
