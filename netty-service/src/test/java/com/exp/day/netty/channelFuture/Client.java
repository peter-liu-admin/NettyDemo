package com.exp.day.netty.channelFuture;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @Author: PeterLiu
 * @Date: 2023/11/9 0:10
 * @Description:
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
                    }
                })
                //异步非阻塞方法：主线程发起调用，真正执行连接的是nio线程。连接建立时间可能有1秒以上
                .connect(new InetSocketAddress("localhost", 9999));

        //方式1：同步方法，阻塞当前线程，直到nio线程建立连接完毕
//        channelFuture.sync();
//        Channel channel = channelFuture.channel();
//        log.debug("channel:{}", channel);
//        channel.writeAndFlush("hello!");

        //方式2：在Nio线程连接建立好后，会调用回调方法(异步调用)
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = channelFuture.channel();
                log.debug("channel:{}", channel);
                channel.writeAndFlush("hello!");
            }
        });
    }
}
