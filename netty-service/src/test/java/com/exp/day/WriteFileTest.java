package com.exp.day;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/20 23:48
 * @Description: 集中写
 */
public class WriteFileTest {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("我是");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("一名");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("程序员");
        try (FileChannel channel = new RandomAccessFile("savedFile.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (IOException e) {

        }
    }
}
