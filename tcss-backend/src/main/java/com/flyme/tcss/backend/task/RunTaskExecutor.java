package com.flyme.tcss.backend.task;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 运行任务执行器
 *
 * @author xiaodao
 * @date 2022/12/23
 */
@Component
public class RunTaskExecutor {

    private volatile ThreadPoolExecutor executor;

    private final static int maximumConcurrency = 50;

    private final static int keepAliveSeconds = 300;

    protected void execute(Runnable task) {
        getExecutor().execute(task);
    }

    private ThreadPoolExecutor getExecutor() {
        if (executor == null) {
            synchronized (executor) {
                if (executor == null) {
                    ThreadPoolExecutor executor = new ThreadPoolExecutor(
                            maximumConcurrency,
                            Integer.MAX_VALUE,
                            keepAliveSeconds,
                            TimeUnit.SECONDS,
                            new LinkedBlockingDeque<Runnable>());
                    executor.allowCoreThreadTimeOut(true);
                }
            }
        }
        return executor;
    }
}
