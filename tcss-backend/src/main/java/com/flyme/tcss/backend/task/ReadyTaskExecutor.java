package com.flyme.tcss.backend.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 就绪任务执行器
 *
 * @author xiaodao
 * @date 2022/12/23
 */
@Slf4j
@Component
public class ReadyTaskExecutor {
    // 就绪队列
    private final LinkedBlockingQueue<ReadyTask> readyQueue = new LinkedBlockingQueue<>();
    // 阻塞队列
    private final LinkedBlockingQueue<ReadyTask> blockQueue = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init() {
        Thread executingThread = new Thread(() -> {
            while (true) {
                ReadyTask readyTask = null;
                try {
                    if (readyQueue.isEmpty() && !blockQueue.isEmpty()) {
                        readyTask = blockQueue.take();
                    } else {
                        readyTask = readyQueue.take();
                    }
                    if (readyTask.isReady()) {
                        readyTask.run();
                    } else {
                        blockQueue.offer(readyTask);
                    }

                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                    if (readyTask != null) {
                        readyTask.onError();
                    }
                }
            }
        });
        executingThread.setDaemon(true);
        executingThread.start();
    }

    public void submit(ReadyTask readyTask) {
        readyQueue.offer(readyTask);
    }

}
