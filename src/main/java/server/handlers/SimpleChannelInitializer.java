package server.handlers;

import client.handlers.SimpleClientChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import server.codecs.*;

/**
 * Initializes a {@link io.netty.channel.Channel} and adds the processing pipeline for the channel
 */
public class SimpleChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new ByteToFooDecoder());
        channel.pipeline().addLast("FooToProxiedEncoder" , new FooToProxiedEncoder());
        channel.pipeline().addLast("ProxyEncoder" , new ProxyEncoder());
        channel.pipeline().addLast("FrontendProxyHandler" , new FrontendProxyHandler("localhost" , 9055));
        channel.pipeline().addLast("FooToResponseEncoder" , new FooToResponseEncoder());
        channel.pipeline().addLast(new ResponseToByteEncoder());
        channel.pipeline().addLast(new SimpleChannelHandler());

    }
}
