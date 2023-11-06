package com.exp.day.netty.eventloop;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @Author: PeterLiu
 * @Date: 2023/11/7 0:13
 * @Description:
 */
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
                    }
                })
                .connect(new InetSocketAddress("localhost", 9999))
                .sync()
                .channel();

        System.out.println(channel);
        System.out.println("");
    }
}
