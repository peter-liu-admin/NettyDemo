package com.exp.day;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.exp.day.utils.ByteBufferUtil.debugAll;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/20 23:07
 * @Description:
 */
@Slf4j
public class ByteBufferStringTest {
    public static void main(String[] args) {
       //1.String转ByteBuffer
        String str="lzh666";
        ByteBuffer buffer= ByteBuffer.allocate(20);
        buffer.put(str.getBytes());
        debugAll(buffer);
        //2.charset
        ByteBuffer buf= StandardCharsets.UTF_8.encode(str);
        debugAll(buf);
        //3.wrap
        ByteBuffer buff=ByteBuffer.wrap(str.getBytes());
        debugAll(buff);

        String res=StandardCharsets.UTF_8.decode(buf).toString();
        log.info("res:{}",res);

        buffer.flip();//切换为模式
        String resStr=StandardCharsets.UTF_8.decode(buffer).toString();
        log.info("resStr:{}",resStr);

    }
}
