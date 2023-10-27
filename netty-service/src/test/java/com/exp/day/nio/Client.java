package com.exp.day.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/22 21:50
 * @Description:
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        final SocketChannel clientChannel = SocketChannel.open();
        clientChannel.connect(new InetSocketAddress("localhost",8999));
        clientChannel.write(Charset.defaultCharset().encode("0123456789abcd"));
        clientChannel.write(Charset.defaultCharset().encode("55555ter\n"));
        System.in.read();
    }
}
