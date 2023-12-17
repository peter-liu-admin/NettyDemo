package com.exp.day.netty.async;

import java.util.UUID;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: PeterLiu
 * @Date: 2023/12/17 23:59
 * @Description:
 */
public class NamedThreadFactory implements ThreadFactory {

    private String name;

    public NamedThreadFactory(String name){
        this.name=name;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable,   name+"--" + UUID.randomUUID().toString());
        thread.setDaemon(false);
        return thread;
    }
}
