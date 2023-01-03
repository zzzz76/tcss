package com.flyme.tcss.backend.service;

import com.flyme.tcss.backend.BackendApplicationTest;
import com.flyme.tcss.backend.domain.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author xiaodao
 * @date 2022/12/22
 */
@Slf4j
public class CaseServiceTest extends BackendApplicationTest {
    @Autowired
    private CaseService caseService;

    @Test
    public void testgetTestCase() {
        TestCase testCase = caseService.getTestCase(1L);
        log.info("获取测试用例，testCase:{}", testCase);
    }
}
