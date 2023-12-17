package com.exp.day.netty.futureClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @Author: PeterLiu
 * @Date: 2023/11/13 22:39
 * @Description:
 */
@Slf4j
public class closeFutureClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture future = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 9000));
        Channel channel = future.sync().channel();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String nextLine = scanner.nextLine();
                    if ("q".equals(nextLine)) {
                        channel.close();
                        break;
                    }
                    channel.writeAndFlush(nextLine);
                }
            }
        }, "USER_INPUT_THREAD").start();

        //方式一：同步关闭
        ChannelFuture closeFuture = channel.closeFuture();
//        log.debug("等待关闭");
//        closeFuture.sync();

        //方式二：异步关闭
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("after processing");
                group.shutdownGracefully();
            }
        });

    }
}
