package client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import model.ClientFoo;
import model.Foo;

public class SimpleClientChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Foo foo = new Foo("bar", "baz");
        System.out.println(String.format("Sending %s from %s" , foo.toString() , getClass().getCanonicalName()));
        ctx.writeAndFlush(new ClientFoo(foo));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("Closing channel %s, due to %s", ctx.channel().id(), ctx.channel().config().toString()));
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(((Foo) msg).toString());
        ctx.close();
    }
}
