package com.exp.day;

import com.example.server.ServerApplication;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/18 23:07
 * @Description:
 */
@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class ByteBufferTest {

    public static void main(String[] args) {
        try{
            //a.获取文件通道
            FileChannel channel = new FileInputStream("dataFile.txt").getChannel();
            //b.准备缓冲区
            ByteBuffer buffer=ByteBuffer.allocate(10);
//            //c.从通道里面读取数据，并向缓冲区写数据 TODO: 这种写法只能单次处理
//            channel.read(buffer);
//            //d.切换到读模式
//            buffer.flip();
//            //e.当缓冲区还剩有数据时，打印数据
//            while(buffer.hasRemaining()){
//                byte b = buffer.get();
//                log.info("打印内容:{}",(char)b);
//            }

            // TODO: 实际是多次处理
            while(true){
                //c1.从通道里面读取数据，并向缓冲区写数据
                int len = channel.read(buffer);
                log.info("读取到的字节数:{}",len);
                if(len==-1){
                    break;
                }
                //d1.切换到读模式
                buffer.flip();
                //e1.当缓冲区还剩有数据时，打印数据
                while(buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.info("读取到的字节:{}",(char)b);
                }
                //f1.切换到写模式
                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * FileChannel
     */
//    @Test
//    public void firstDemo(){
//        try{
//            //a.获取文件通道
//            FileChannel channel = new FileInputStream("dataFile.txt").getChannel();
//            //b.准备缓冲区
//            ByteBuffer buffer=ByteBuffer.allocate(10);
//            //c.从通道里面读取数据，并向缓冲区写数据
//            channel.read(buffer);
//            //d.切换到读模式
//            buffer.flip();
//            //e.当缓冲区还剩有数据时，打印数据
//            while(buffer.hasRemaining()){
//                byte b = buffer.get();
//                System.out.println((char)b);
//            }
//
//        }catch (Exception e){
//
//        }
//    }
}
