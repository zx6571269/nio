package zx.nio;

import org.junit.Test;

import java.io.*;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by zenxin on 2018/11/9.
 */
public class NioFileTest {


    //分散读和聚集写
    @Test
    public void GratheredAndTest() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");

        //1.获取通道
        FileChannel inChannel = randomAccessFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);

        //分散读
        ByteBuffer[] buffers = {buffer1,buffer2};

        inChannel.read(buffers);
        for (ByteBuffer byteBuffer : buffers) {
            byteBuffer.flip();
        }

        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));


        RandomAccessFile raf2 = new RandomAccessFile("2.txt","rw");
        FileChannel outChannel = raf2.getChannel();

        outChannel.write(buffers);

    }


    //直接缓冲区读写文件
    @Test
    public void DirectTest01() throws IOException {
        //设置通道为读，并且读取数据
        FileChannel inChannel   = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);
        //设置通道为写，并设置读，写，StandardOpenOption.CREATE 创建一个文件，当拥有相同文件时，覆盖文件
        FileChannel outChannel  = FileChannel.open(Paths.get("2.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,
                                                                            StandardOpenOption.CREATE);
        //读取inChannel的数据读取，并写
        outChannel.transferFrom(inChannel,0,inChannel.size());
        inChannel.close();
        outChannel.close();
        }




    //非直接缓冲，读，写文件
    @Test
    public void NoDirectTest() throws Exception {
        FileInputStream fis = new FileInputStream("1.png");
        FileOutputStream fos = new FileOutputStream("3.png");

        //获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (inChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();//切换为读模式
            outChannel.write(byteBuffer);//将缓冲区的数据写入到通道中
            byteBuffer.clear();//清空缓冲
        }

        if (outChannel !=null){
            outChannel.close();
        }
        if (inChannel != null){
            inChannel.close();
        }


    }
}
