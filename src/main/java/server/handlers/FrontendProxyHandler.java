package server.handlers;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import model.ProxiedFoo;

public class FrontendProxyHandler extends ChannelInboundHandlerAdapter {

    private final String remoteHost;
    private final int remotePort;
    public static int counter = 0;
    // As we use inboundChannel.eventLoop() when buildling the Bootstrap this does not need to be volatile as
    // the server2OutboundChannel will use the same EventLoop (and therefore Thread) as the inboundChannel.
    private Channel server2OutboundChannel;

    public FrontendProxyHandler(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final Channel inboundChannel = ctx.channel();

        // Start the connection attempt to SERVER 2
        Bootstrap server2Bootstrap = new Bootstrap();
        server2Bootstrap.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .handler(new BackendProxyHandler(inboundChannel))
                .option(ChannelOption.AUTO_READ, false);
        ChannelFuture server2Future = server2Bootstrap.connect(remoteHost, remotePort);

        server2OutboundChannel = server2Future.channel();
        server2Future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    // connection complete start to read first data
                    inboundChannel.read();
                } else {
                    // Close the connection if the connection attempt has failed.
                    inboundChannel.close();
                }
            }
        });
    }

    // You can keep this the same below or use the commented out section
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {

        if (server2OutboundChannel.isActive()) {
            if(server2OutboundChannel.isWritable()) {
                //This was the major pain point!!! you need to write only bytes, not an object, the interface is misleading
                ChannelFuture cf = server2OutboundChannel.write(((ProxiedFoo)msg).process());
                server2OutboundChannel.flush();
                cf.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (future.isSuccess()) {
                            // was able to flush out data, start to read the next chunk
                            //System.out.println(counter++ +" Bytes Before UnWritable->" + server2OutboundChannel.bytesBeforeUnwritable());
                            ctx.channel().read();
                        } else {
                            future.channel().close();
                        }
                    }
                });
            }else{
                System.out.println("Channel is no longer writeable");
                System.out.println(server2OutboundChannel.bytesBeforeUnwritable());
                System.out.println(server2OutboundChannel.bytesBeforeWritable());
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (server2OutboundChannel != null) {
            closeOnFlush(server2OutboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
