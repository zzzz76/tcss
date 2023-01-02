package com.flyme.tcss.backend.factory;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录服务实例的状态
 *
 * @author xiaodao
 * @date 2022/12/26
 */
@Component
public class InstanceFactory {

    private final Map<String, Integer> records = new ConcurrentHashMap<>();

    @Value("${provider.name}")
    private String serviceName;

    @Value("${provider.task-num}")
    private Integer maxTaskNum;

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    // 释放锁定的服务实例
    public void releaseInstance(String instanceName) {
        if (records.get(instanceName) > 0) {
            synchronized (records) {
                int taskNum = records.get(instanceName);
                if (taskNum > 0) {
                    records.put(instanceName, taskNum - 1);
                }
            }
        }
    }

    // 获取空闲的服务实例
    public String getInstance() {
        List<Instance> instances = getInstances(serviceName);
        Collections.shuffle(instances);
        for (Instance instance: instances) {
            String instanceName = instance.getIp() + ":" + instance.getPort();
            if (!records.containsKey(instanceName)) {
                synchronized (records) {
                    if (!records.containsKey(instanceName)) {
                        records.put(instanceName, 1);
                        return instanceName;
                    }
                }
            }

            if (records.get(instanceName) < maxTaskNum) {
                synchronized (records) {
                    int taskNum = records.get(instanceName);
                    if (taskNum < maxTaskNum) {
                        records.put(instanceName, taskNum + 1);
                        return instanceName;
                    }
                }
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
            return Collections.emptyList();
        }
    }
}
