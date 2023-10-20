package com.exp.day;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.exp.day.utils.ByteBufferUtil.debugAll;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/20 23:38
 * @Description: 分散读
 */
public class ReadFileTest {
    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("words.txt", "r").getChannel()) {
            ByteBuffer buf_1 = ByteBuffer.allocate(2);
            ByteBuffer buf_2 = ByteBuffer.allocate(3);
            ByteBuffer buf_3 = ByteBuffer.allocate(4);

            channel.read(new ByteBuffer[]{buf_1,buf_2,buf_3});
            //切换读模式
            buf_1.flip();
            buf_2.flip();
            buf_3.flip();

            debugAll(buf_1);
            debugAll(buf_2);
            debugAll(buf_3);
        } catch (IOException e) {
        }
    }
}
