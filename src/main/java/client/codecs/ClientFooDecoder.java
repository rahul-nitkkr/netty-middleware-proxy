package client.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import model.Foo;

import java.nio.charset.Charset;
import java.util.List;

public class ClientFooDecoder extends ByteToMessageDecoder {
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int len1 = in.readInt();
        String val1 = in.readCharSequence(len1 , charset).toString();
        int len2 = in.readInt();
        String val2 = in.readCharSequence(len2 , charset).toString();
        System.out.println(String.format("vals : %s  , %s" , val1, val2));
        out.add(new Foo(val1 , val2));
    }
}
