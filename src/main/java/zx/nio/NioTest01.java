package zx.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2018/10/31.
 */
public class NioTest01 {


    @Test
    public void test03(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);

        System.out.println( buffer.isDirect());

    }

    @Test
    public void nioTest2(){
        String str = "abcde";

        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(str.getBytes());

        buffer.flip();

        byte[] bytes =  new byte[buffer.limit()];

        buffer.get(bytes,0,2);

        //mark标记
        buffer.mark();

        buffer.get(bytes,0,2);

        //reset将会回到mark标记的位置
        buffer.reset();

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

    }

    @Test
    public void nioTest1(){
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println("-------------初始化位置----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        System.out.println("-------------put后位置----------------");
        buffer.put(str.getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //切换到读取数据
        buffer.flip();
        System.out.println("-------------flip后位置----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes,0,2);
        System.out.println(new String(bytes,0,bytes.length));

        System.out.println("-------------get后位置----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.get(bytes,0,2);

        System.out.println("-------------第二次get后位置----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println(new String(bytes,0,bytes.length));


        //clear后数据依然存在
        buffer.clear();
        System.out.println("-------------clear后位置----------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        System.out.println((char)buffer.get(4));



    }
}
