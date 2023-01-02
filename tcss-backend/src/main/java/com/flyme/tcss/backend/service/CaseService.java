package com.flyme.tcss.backend.service;

import com.flyme.tcss.backend.domain.TestCase;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
public interface CaseService {
    /**
     * 提交测试案例
     */
    void submitTestCase(TestCase testCase);

}
