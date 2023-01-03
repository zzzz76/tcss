package com.flyme.tcss.backend.task;

import com.flyme.tcss.backend.tools.SpringBean;

/**
 * 运行队列中的任务
 *
 * @author xiaodao
 * @date 2022/12/23
 */
public abstract class RunTask implements Runnable {

    public void submit() {
        SpringBean.getBean(RunTaskExecutor.class).execute(this);
    }

}
