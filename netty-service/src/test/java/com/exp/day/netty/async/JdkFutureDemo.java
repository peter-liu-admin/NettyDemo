package com.exp.day.netty.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author: PeterLiu
 * @Date: 2023/12/17 23:44
 * @Description: future主要是在线程之间传递结果的角色
 */
@Slf4j
public class JdkFutureDemo {
    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2, new NamedThreadFactory("异步线程"));

        //异步线程提交任务
        Future<Object> future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                log.debug("执行等待:{}",Thread.currentThread().getName());
                Thread.sleep(1000);
                return 100;
            }
        });

        //主线程获取异步线程的结果
        log.debug("等待获取结果：{}",Thread.currentThread().getName());
        log.debug("获取到的结果是:{}",future.get());
    }
}
