package remote.server.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import remote.server.codecs.ByteToFooDecoder;
import remote.server.codecs.FooToProxiedEncoder;
import remote.server.codecs.ResponseToByteEncoder;


/**
 * Initializes a {@link io.netty.channel.Channel} and adds the processing pipeline for the channel
 */
public class SimpleChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new ByteToFooDecoder());
        channel.pipeline().addLast(new FooToProxiedEncoder());
        channel.pipeline().addLast(new ResponseToByteEncoder());
        channel.pipeline().addLast(new SimpleChannelHandler());

    }
}
