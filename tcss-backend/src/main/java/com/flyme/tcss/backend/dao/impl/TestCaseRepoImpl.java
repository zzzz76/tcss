package com.flyme.tcss.backend.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyme.tcss.backend.dao.TestCaseRepo;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.backend.mapper.TestCaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
@Repository
public class TestCaseRepoImpl extends ServiceImpl<TestCaseMapper, TestCase> implements TestCaseRepo {
}
