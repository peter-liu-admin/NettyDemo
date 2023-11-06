package com.exp.day.netty.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: PeterLiu
 * @Date: 2023/11/6 23:39
 * @Description:
 */
@Slf4j
public class NettyEventLoopDemo {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
//        //1、普通任务
//        group.next().submit(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                Thread.sleep(5000);
//                log.info(Thread.currentThread()+":我是米老鼠");
//            }
//        });
//        log.info(Thread.currentThread()+":你是？");

        //2、定时任务
        group.next().scheduleAtFixedRate(()->{
            log.debug("AAAAAA");
        },0,2, TimeUnit.SECONDS);

        log.debug("CCCCCCCC");
    }
}
