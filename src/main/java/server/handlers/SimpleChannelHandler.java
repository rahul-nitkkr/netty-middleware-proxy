package server.handlers;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import model.Foo;

import java.util.function.Function;

/**
 * Handles incoming requests to the server and delegates decoding of requests
 * for {@link io.netty.buffer.ByteBuf}'s received from the client
 * <p>
 * Client --------> channelHandler ----------> Decoder
 */
public class SimpleChannelHandler extends ChannelInboundHandlerAdapter {
    Function<String, String> responseFunc = val -> String.format("Response:%s", val);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("received!!" + msg.toString());
        ChannelFuture future = ctx.writeAndFlush(getResponseFoo((Foo)msg));
        future.addListener(ChannelFutureListener.CLOSE);
    }


    private Foo getResponseFoo(Foo foo) {
        return new Foo(responseFunc.apply(foo.getVal1()), responseFunc.apply(foo.getVal2()));
    }
}
