package com.flyme.tcss.backend.factory;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flyme.tcss.backend.dao.TestInstanceRepo;
import com.flyme.tcss.common.domain.TestInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 记录服务实例的状态
 *
 * @author xiaodao
 * @date 2022/12/26
 */
@Slf4j
@Component
public class InstanceFactory {

    @Value("${provider.name}")
    private String serviceName;

    @Autowired
    private TestInstanceRepo testInstanceRepo;

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    // 获取空闲的服务实例
    public TestInstance getInstance() {
        List<Instance> instances = getInstances(serviceName);
        List<String> urlList = new ArrayList<>();
        for (Instance instance: instances) {
            urlList.add(instance.getIp() + ":" + instance.getPort());
        }

        QueryWrapper<TestInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("url", urlList).orderByAsc("task_num");
        List<TestInstance> testInstanceList = testInstanceRepo.list(queryWrapper);
        for (TestInstance testInstance: testInstanceList) {
            if (testInstance.getTaskNum() < testInstance.getMaxTaskNum()) {
                return testInstance;
            }
        }
        return null;
    }

    // 获取该微服务的所有健康实例
    private List<Instance> getInstances(String serviceName) {
        NamingService namingService = discoveryProperties.namingServiceInstance();
        try {
            return namingService.selectInstances(serviceName, true);
        } catch (NacosException e) {
            log.error(e.getErrMsg(), e);
            return Collections.emptyList();
        }
    }
}
