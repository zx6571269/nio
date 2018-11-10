package zx.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Administrator on 2018/11/10.
 */
public class CharSetTest {

    @Test
    public void ChrSetTest01() throws CharacterCodingException {

        Charset charsetGBK = Charset.forName("GBK");

        CharsetEncoder charsetEncoder = charsetGBK.newEncoder();
        CharsetDecoder charsetDecoder = charsetGBK.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);

        charBuffer.put("极速");

        charBuffer.flip();
        //将CharBuffer 的数据进行编码,存储到ByteBuffer
        ByteBuffer buffer = charsetEncoder.encode(charBuffer);

        for (int i=0;i<4;i++){
            System.out.println(buffer.get());
        }

        buffer.flip();
        CharBuffer decode = charsetDecoder.decode(buffer);

        System.out.println(decode.toString());

    }
}
