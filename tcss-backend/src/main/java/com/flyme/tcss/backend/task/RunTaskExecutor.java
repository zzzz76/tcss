package com.flyme.tcss.backend.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 运行任务执行器
 *
 * @author xiaodao
 * @date 2022/12/23
 */
@Component
public class RunTaskExecutor {

    private ThreadPoolExecutor executor;

    private final static int maximumConcurrency = 10;

    private final static int keepAliveSeconds = 10;

    protected void execute(Runnable task) {
        executor.execute(task);
    }

    @PostConstruct
    private void init() {
        executor = new ThreadPoolExecutor(
                maximumConcurrency,
                Integer.MAX_VALUE,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
        executor.allowCoreThreadTimeOut(true);
    }
}
