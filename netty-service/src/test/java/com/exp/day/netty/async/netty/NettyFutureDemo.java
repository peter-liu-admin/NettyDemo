package com.exp.day.netty.async.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @Author: PeterLiu
 * @Date: 2023/12/18 0:22
 * @Description:
 */
@Slf4j
public class NettyFutureDemo {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();

        Future<String> future = eventLoop.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("等待执行：{}", Thread.currentThread().getName());
                Thread.sleep(1000);
                return "张三";
            }
        });

        //方式一：主线程同步获取
//        log.debug("等待获取结果：{}",Thread.currentThread().getName());
//        log.debug("获取到的结果是:{}",future.get());

        //方式二：当前线程异步获取
        future.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                log.debug("等待获取结果：{}", Thread.currentThread().getName());
                if (future.isDone()) {
                    log.debug("获取到的结果是:{}", future.getNow());
                }
            }
        });
    }
}
