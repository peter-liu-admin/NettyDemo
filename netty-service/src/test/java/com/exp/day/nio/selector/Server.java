package com.exp.day.nio.selector;

import com.exp.day.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/24 23:03
 * @Description: 演示选择器
 * 4种事件：accept--会在有连接请求时触发；connect--是客户端的操作，连接建立后触发；read--可读事件；write--可写事件
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //1、创建selector选择器，管理多个channel通道
        final Selector selector = Selector.open();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2、建立选择器和通道的关系。SelectionKey就是将来事件发生后，通过它可以知道是哪个通道的事件
        final SelectionKey sscKey = ssc.register(selector, 0, null);
        //sscKey只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("sscKey:{}", sscKey);
        ssc.bind(new InetSocketAddress(8999));

        while (true) {
            log.debug("没有事件发生，线程阻塞");
            //3、调用select方法。没有事件发生，线程阻塞，有事件，线程才会运行。减少不必要的线程运行
            selector.select();
            //4、处理事件,selectedKeys方法返回所有发生的事件
            final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                final SelectionKey selectionKey = iterator.next();
                log.debug("selectionKey:{}", selectionKey);
                final ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                final SocketChannel sc = channel.accept();
                log.debug("sc:{}", sc);
            }

        }
    }
}
