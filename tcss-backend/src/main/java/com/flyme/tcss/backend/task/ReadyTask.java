package com.flyme.tcss.backend.task;

/**
 * 就绪队列中的任务
 *
 * @author xiaodao
 * @date 2022/12/23
 */
public abstract class ReadyTask {
    // 提交任务至就绪队列
    public void submit() {
        SpringBean.getBean(ReadyTaskExecutor.class).submit(this);
    }

    // 执行任务
    protected abstract void run();
    // 任务是否可以执行
    protected abstract boolean isReady();
    // 任务执行异常的处理方法
    protected abstract void onError();
}
