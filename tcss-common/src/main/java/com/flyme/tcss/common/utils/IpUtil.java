package com.flyme.tcss.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xiaodao
 * @date 2023/1/4
 */
@Slf4j
public class IpUtil {
    public static String getServiceIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.error("本地ip获取异常", e);
        }
        return address.getHostAddress();
    }
}
