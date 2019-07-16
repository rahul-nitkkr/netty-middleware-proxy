package server.codecs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import model.MessageTypes;
import model.ProxiedFoo;

import java.nio.charset.Charset;

public class ProxyEncoder extends MessageToByteEncoder<ProxiedFoo> {
    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ProxiedFoo msg, ByteBuf out) throws Exception {
        out.writeInt(MessageTypes.PROXIED.ordinal());
        out.writeInt(msg.getVal1().length());
        out.writeCharSequence(msg.getVal1().subSequence(0, msg.getVal1().length()) , Charset.forName("UTF-8"));
        out.writeInt(msg.getVal2().length());
        out.writeCharSequence(msg.getVal2().subSequence(0, msg.getVal2().length()) , Charset.forName("UTF-8"));
    }
}
