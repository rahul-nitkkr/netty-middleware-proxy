package client.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import model.ClientFoo;
import model.MessageTypes;

import java.nio.charset.Charset;

public class ClientFooEncoder extends MessageToByteEncoder<ClientFoo> {
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ClientFoo foo, ByteBuf out) throws Exception {
        System.out.println(String.format("Sending %s", foo.toString()));
        out.writeInt(MessageTypes.PROXIED.ordinal());
        processString(foo.getVal1(), out);
        processString(foo.getVal2(), out);
    }

    private void processString(String value, ByteBuf out) {
        int len = value.length();
        out.writeInt(len);
        System.out.println(value.subSequence(0, len).toString());
        out.writeCharSequence(value.subSequence(0, len), charset);
    }
}
