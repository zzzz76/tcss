package com.flyme.tcss.backend.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyme.tcss.backend.dao.TestRecordRepo;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.common.domain.TestRecord;
import com.flyme.tcss.backend.mapper.TestRecordMapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
@Repository
public class TestRecordRepoImpl extends ServiceImpl<TestRecordMapper, TestRecord> implements TestRecordRepo {
}
