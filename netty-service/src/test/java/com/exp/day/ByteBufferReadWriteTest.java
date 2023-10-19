package com.exp.day;

import java.nio.ByteBuffer;

import static com.exp.day.utils.ByteBufferUtil.debugAll;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/19 23:04
 * @Description:
 */
public class ByteBufferReadWriteTest {
    public static void main(String[] args) {
        //分配10字节大小空间
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //使用put方法向buffer里面存入'a'
        buffer.put((byte) 0x61);
        //调用工具类输出内容
        debugAll(buffer);
        buffer.put(new byte[]{0x62,0x63,0x64});
        debugAll(buffer);
        //position指向4的位置，因此为0
        System.out.println("buffer.get() = " + buffer.get());
        //切换为读模式
        buffer.flip();
        System.out.println("切换模式");
        System.out.println("buffer.get() = " + buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);
        buffer.put(new byte[]{0x65,0x66});
        debugAll(buffer);
    }
}
