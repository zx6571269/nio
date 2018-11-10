package zx.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2018/11/10.
 */
public class PipeTest {



    public static void main(String[] args) throws InterruptedException, IOException {
        final Pipe pipe = Pipe.open();
        //向管道写入数据
        final Pipe.SinkChannel sink = pipe.sink();
        final Pipe.SourceChannel source = pipe.source();
          Thread threada = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    String str = "测试数据";


                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(str.getBytes());
                    buffer.flip();

                    while (buffer.hasRemaining()){
                        sink.write(buffer);
                    }
                    System.out.println("1:"+new String(buffer.array(),0,8));
                    buffer.clear();

                    sink.close();
                    System.out.println("11");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread threadb = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = source.read(buffer);

                    buffer.flip();
                    System.out.println(new String(buffer.array(),0,read));
                    System.out.println("22");

                    source.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threada.start();
        threadb.start();


    }






}
