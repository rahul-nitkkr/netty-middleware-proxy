package model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ProxiedFoo extends BaseFoo {
    private final String v1;
    private final String v2;


    public ProxiedFoo(String v1, String v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "ProxiedFoo{" +
                "val1='" + v1 + '\'' +
                ", val2='" + v2 + '\'' +
                '}';
    }

    @Override
    public String getVal1() {
        return v1;
    }

    @Override
    public String getVal2() {
        return v2;
    }

    public ByteBuf process() {
        ByteBuf out = Unpooled.buffer();
        out.writeInt(MessageTypes.PROXIED.ordinal());
        out.writeInt(getVal1().length());
        out.writeCharSequence(getVal1().subSequence(0, getVal1().length()) , Charset.forName("UTF-8"));
        out.writeInt(getVal2().length());
        out.writeCharSequence(getVal2().subSequence(0, getVal2().length()) , Charset.forName("UTF-8"));

        return out;
    }
}
