package com.exp.day.netty.futureClient;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Author: PeterLiu
 * @Date: 2023/11/9 0:09
 * @Description:
 */
@Slf4j
public class Sever {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    // NioEventLoopGroup，可以简单理解为 `线程池 + Selector`
                    .group(group)
                    // 选择服务 Scoket 实现类，其中 NioServerSocketChannel 表示基于 NIO 的服务器端实现
                    .channel(NioServerSocketChannel.class)
                    // 接下来添加的处理器都是给 SocketChannel 用的，而不是给 ServerSocketChannel。ChannelInitializer 处理器（仅执行一次），
                    // 它的作用是待客户端 SocketChannel 建立连接后，执行 initChannel 以便添加更多的处理器
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            // SocketChannel 的处理器，解码 ByteBuf => String
                            nioSocketChannel.pipeline().addLast(new StringDecoder());
                            // SocketChannel 的业务处理器，使用上一个处理器的处理结果
                            nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.info("msg:{}", msg);
                                }
                            });
                        }
                    }).bind(9000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
