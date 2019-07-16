package server.codecs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import model.Foo;

import java.util.List;
import java.util.function.Function;

public class FooToResponseEncoder extends MessageToMessageDecoder<Foo> {
    Function<String, String> getResponseString = s -> String.format("NoProxy: %s" , s);

    @Override
    protected void decode(ChannelHandlerContext ctx, Foo foo, List<Object> list) throws Exception {
        System.out.println(getResponseFoo(foo));
        list.add(getResponseFoo(foo));
    }

    private Foo getResponseFoo(Foo foo) {
        return new Foo(getResponseString.apply(foo.getVal1()) , getResponseString.apply(foo.getVal2()));
    }
}
