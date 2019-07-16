package server.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import model.ProxiedFoo;

import java.util.List;
import java.util.function.Function;

public class FooToProxiedEncoder extends MessageToMessageDecoder<ProxiedFoo> {
    Function<String, String> getResponseString = s -> String.format("Proxy: %s" , s);

    @Override
    protected void decode(ChannelHandlerContext ctx, ProxiedFoo foo, List<Object> list) throws Exception {
        System.out.println(getResponseFoo(foo));
        list.add(getResponseFoo(foo));
    }

    private ProxiedFoo getResponseFoo(ProxiedFoo foo) {
        return new ProxiedFoo(getResponseString.apply(foo.getVal1()) , getResponseString.apply(foo.getVal2()));
    }
}
