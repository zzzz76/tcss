package com.flyme.tcss.backend.service;

import com.flyme.tcss.common.domain.TestCase;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
public interface CaseService {
    /**
     * 提交测试用例
     */
    void submitTestCase(TestCase testCase);

    /**
     * 获取测试用例
     */
    TestCase getTestCase(Long id);

}
