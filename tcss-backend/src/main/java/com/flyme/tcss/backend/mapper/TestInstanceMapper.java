package com.flyme.tcss.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flyme.tcss.common.domain.TestCase;
import com.flyme.tcss.common.domain.TestInstance;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
@Mapper
public interface TestInstanceMapper extends BaseMapper<TestInstance> {
}
