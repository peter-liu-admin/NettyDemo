package com.exp.day;

import java.nio.ByteBuffer;

import static com.exp.day.utils.ByteBufferUtil.debugAll;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/21 17:28
 * @Description: 演示黏包和半包问题
 */
public class ByteBufferDemo {
    public static void main(String[] args) {
        String str1 = "Hello,world\nI am a boy\nHo";
        String str2 = "w are you?\n";

        //分配大小
        ByteBuffer buffer = ByteBuffer.allocate(40);
        buffer.put(str1.getBytes());
        split(buffer);
        buffer.put(str2.getBytes());
        split(buffer);
    }

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
}
