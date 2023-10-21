package com.exp.day;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @Author: PeterLiu
 * @Date: 2023/10/21 18:18
 * @Description: 测试TransferTo方法
 */
public class FileChannelTransferToDemo {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("dataFile.txt").getChannel();//读
                FileChannel to = new FileOutputStream("toData.txt").getChannel();//写
        ) {
            //效率高，底层会使用操作系统的零拷贝.最大限制2G
//            from.transferTo(0,from.size(),to);
            //超过2G,需要进行循环处理
            long size = from.size();
            for (long leftSize = size; leftSize > 0; ) {
                leftSize -= from.transferTo((size - leftSize), size, to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
