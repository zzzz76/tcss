package com.flyme.tcss.tester.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flyme.tcss.common.domain.TestInstance;
import com.flyme.tcss.common.utils.IpUtil;
import com.flyme.tcss.tester.dao.TestInstanceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author xiaodao
 * @date 2023/1/3
 */
@Slf4j
@Component
public class StartupRunner {

    @Value("${provider.task-num}")
    private Integer maxTaskNum;
    @Value("${provider.name}")
    private String serverName;
    @Value("${server.port}")
    private String port;
    @Autowired
    private TestInstanceRepo testInstanceRepo;

    @Bean
    @Primary
    public TestInstance initialInstance() {
        String url = IpUtil.getServiceIp() + ":" + port;

        QueryWrapper<TestInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        TestInstance testInstance = testInstanceRepo.getOne(queryWrapper);

        if (testInstance != null) {
            testInstance.setTaskNum(0);
            testInstance.setVersion(0L);
            testInstanceRepo.updateById(testInstance);
        } else {
            testInstance = new TestInstance();
            testInstance.setName(serverName);
            testInstance.setUrl(url);
            testInstance.setTaskNum(0);
            testInstance.setMaxTaskNum(maxTaskNum);
            testInstanceRepo.save(testInstance);
        }
        log.info("初始化测试服务实例，instance:{}", testInstance);
        return testInstance;
    }
}
