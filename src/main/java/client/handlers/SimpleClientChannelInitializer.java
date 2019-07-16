package client.handlers;

import client.codecs.ClientFooDecoder;
import client.codecs.ClientFooEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class SimpleClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ClientFooEncoder());
        socketChannel.pipeline().addLast(new ClientFooDecoder());
        socketChannel.pipeline().addLast(new SimpleClientChannelHandler());
    }
}
