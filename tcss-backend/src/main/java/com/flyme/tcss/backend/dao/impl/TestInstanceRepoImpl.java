package com.flyme.tcss.backend.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyme.tcss.backend.dao.TestInstanceRepo;
import com.flyme.tcss.backend.domain.TestInstance;
import com.flyme.tcss.backend.mapper.TestInstanceMapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
@Repository
public class TestInstanceRepoImpl extends ServiceImpl<TestInstanceMapper, TestInstance> implements TestInstanceRepo {
}
