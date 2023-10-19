package com.exp.day;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import static com.exp.day.utils.ByteBufferUtil.debugAll;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/19 23:40
 * @Description:
 */
@Slf4j
public class ByteBufferReadTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
//        //从头开始读,读到新的数组里面去
//        byte[] newArr = new byte[4];
//        buffer.get(newArr);
//        debugAll(buffer);
//        //相同的api
//        buffer.rewind();
//        debugAll(buffer);
//        System.out.println((char) buffer.get());

        //NOTE:mark记录position的位置，reset将position重置到mark的位置
        log.debug("读取：{}", (char) buffer.get());
        log.debug("读取：{}", (char) buffer.get());
        //标记此时指针的位置，此时position的位置是2
        buffer.mark();
        log.debug("读取：{}",(char)buffer.get());
        log.debug("读取：{}",(char)buffer.get());
        buffer.reset();
        log.debug("读取：{}",(char)buffer.get());
        log.debug("读取：{}",(char)buffer.get());
    }
}
