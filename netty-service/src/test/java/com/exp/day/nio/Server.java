package com.exp.day.nio;

import com.exp.day.utils.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/22 10:40
 * @Description: 使用NIO理解阻塞模式
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //0、分配内存
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1、创建服务器,打开通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //1.1 配置通道为非阻塞，这样accept方法即为非阻塞
        ssc.configureBlocking(false);
        //2、绑定监听的端口
        ssc.bind(new InetSocketAddress(8999));
        //3、管道集合
        List<SocketChannel> channelList = new ArrayList<>();
        //4、循环遍历
        while (true) {
//            log.debug("连接中.....");
            //5、accept 建立与客户端连接， SocketChannel 用来与客户端之间通信。
            //5.1、ssc.configureBlocking(true);时，为阻塞方法，线程停止运行；
            //     ssc.configureBlocking(false);时，为非阻塞方法，线程还是会继续运行，如果没有建立连接，ssc返回为null，否则有值
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                log.debug("已连接---{}", sc);
                sc.configureBlocking(false);
                //6、可能不止一个客户端，所以需要加入到集合里面
                channelList.add(sc);
            }
            for (SocketChannel socketChannel : channelList) {
//                log.debug("读取数据前:{}", socketChannel);
                //7、从通道里面读取数据写到缓冲区里面
                //7.1、sc.configureBlocking(true);时，为阻塞方法，线程停止运行；
                //7.2、sc.configureBlocking(false);时，为非阻塞方法，线程还是会继续运行，如果没有建立连接，read返回为0，否则有值
                final int read = socketChannel.read(buffer);
                if(read>0){
                    //8、切换为读模式
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    //9、切换为写模式
                    buffer.clear();
                    log.debug("读取数据后:{}", socketChannel);
                }
            }
        }
    }
}
