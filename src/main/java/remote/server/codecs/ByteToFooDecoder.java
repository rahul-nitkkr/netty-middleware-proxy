package remote.server.codecs;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import model.ProxiedFoo;

import java.nio.charset.Charset;
import java.util.List;

public class ByteToFooDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4)
            return;
        byteBuf.readInt();
        list.add(createProxiedFoo(byteBuf));
    }

    private ProxiedFoo createProxiedFoo(ByteBuf in) {
        int len1 = in.readInt();
        String val1 = readString(in, len1);

        int len2 = in.readInt();
        String val2 = readString(in, len2);

        System.out.println(String.format("lengths are from remote: %d, %d == vals are : %s , %s", len1, len2, val1, val2));
        return new ProxiedFoo(val1, val2);
    }

    private String readString(ByteBuf in, int readIdx) {
        return in.readCharSequence(readIdx, Charset.forName("UTF-8")).toString();
    }
}

