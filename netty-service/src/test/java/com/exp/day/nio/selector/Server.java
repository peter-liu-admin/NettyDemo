package com.exp.day.nio.selector;

import com.exp.day.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static com.exp.day.utils.ByteBufferUtil.debugAll;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/24 23:03
 * @Description: 演示选择器
 * 4种事件：accept--会在有连接请求时触发；connect--是客户端的操作，连接建立后触发；read--可读事件；write--可写事件
 */
@Slf4j
public class Server {

    private static void split(ByteBuffer byteBuffer) {
        //切换为读模式
        byteBuffer.flip();
        //遍历缓冲区字节
        for (int i = 0; i < byteBuffer.limit(); i++) {
            //get每读取一个字节都会移动一次指针。
            if ('\n' == byteBuffer.get(i)) {
                //获取一条我完整消息的长度
                int len = i + 1 - byteBuffer.position();
                //写入到新的缓存区
                ByteBuffer newBuf = byteBuffer.allocate(len);
                //从byteBuffer里面读，写到newBuf去
                for (int j = 0; j < len; j++) {
                    newBuf.put(byteBuffer.get());
                }
                debugAll(newBuf);
            }
        }
        //压缩未读的
        byteBuffer.compact();
    }

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
            selector.select();//事件发生后，要么处理，要么取消。
            //4、处理事件,selectedKeys方法返回所有发生的事件
            final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                final SelectionKey selectionKey = iterator.next();
                //每处理完一个Key，都要从selectedKeys集合中移除。否则会出现空指针异常
                iterator.remove();
                log.debug("selectionKey:{}", selectionKey);
                //5、区分事件类型
                if (selectionKey.isAcceptable()) {
                    // Returns the channel for which this key was created.  This method will continue to return the channel even after the key is cancelled.
                    final ServerSocketChannel ssChannel = (ServerSocketChannel) selectionKey.channel();
                    //Accepts a connection made to this channel's socket.
                    final SocketChannel sc = ssChannel.accept();
                    //设置为Nio
                    sc.configureBlocking(false);
                    // 分配大小
                    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                    // Registers this channel with the given selector, returning a selection key.
                    final SelectionKey sKey = sc.register(selector, 0, byteBuffer);
                    sKey.interestOps(SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    try {
                        final SocketChannel sc = (SocketChannel) selectionKey.channel();
                        //获取KEY对应的附件
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        //从通道读取数据到缓冲区
                        final int read = sc.read(byteBuffer);
                        //如果客户端正常断开(clientChannel.close();)，read方法返回-1。需要移除Key
                        if (read == -1) {
                            selectionKey.cancel();
                        } else {
                            //分割
                            split(byteBuffer);
                            if (byteBuffer.position() == byteBuffer.limit()) {
                                //如果没有读到完整的消息，那么需要扩容
                                ByteBuffer newBuffer = ByteBuffer.allocate(byteBuffer.capacity() * 2);
                                //切换为读模式
                                byteBuffer.flip();
                                newBuffer.put(byteBuffer);
                                selectionKey.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        //客户端断开后，一定要将Key移除掉，否则会循环抛出异常
                        selectionKey.cancel();
                    }
                }
            }
        }
    }
}
