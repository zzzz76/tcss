package com.flyme.tcss.backend.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiaodao
 * @date 2022/12/23
 */
@Component
public class SpringBean implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public static Object getBean(String beanName, Object... args) {
        return applicationContext.getBean(beanName, args);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBean.applicationContext = applicationContext;
    }
}
