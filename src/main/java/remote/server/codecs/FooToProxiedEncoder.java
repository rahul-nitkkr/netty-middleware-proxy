package remote.server.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import model.Foo;
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

    private Foo getResponseFoo(ProxiedFoo foo) {
        return new Foo(getResponseString.apply(foo.getVal1()) , getResponseString.apply(foo.getVal2()));
    }
}
