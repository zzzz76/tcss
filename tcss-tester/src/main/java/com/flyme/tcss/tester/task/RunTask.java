package com.flyme.tcss.tester.task;

/**
 * 运行队列中的任务
 *
 * @author xiaodao
 * @date 2022/12/23
 */
public abstract class RunTask implements Runnable {

    public void submit() {
        beforeSubmit();
        SpringBean.getBean(RunTaskExecutor.class).execute(this);
    }

    protected abstract void beforeSubmit();

}
