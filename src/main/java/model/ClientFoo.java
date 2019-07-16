package model;

public class ClientFoo {
    private final int len1;
    private final String val1;
    private final int len2;
    private final String val2;

    public ClientFoo(int len1, String val1, int len2, String val2) {
        this.len1 = len1;
        this.val1 = val1;
        this.len2 = len2;
        this.val2 = val2;
    }

    public ClientFoo(Foo foo) {
        this.len1 = foo.getVal1().length();
        this.val1 = foo.getVal1();
        this.len2 = foo.getVal2().length();
        this.val2 = foo.getVal2();
    }

    public int getLen1() {
        return len1;
    }

    public String getVal1() {
        return val1;
    }

    public int getLen2() {
        return len2;
    }

    public String getVal2() {
        return val2;
    }

    @Override
    public String toString() {
        return "ClientFoo{" +
                "len1=" + len1 +
                ", val1='" + val1 + '\'' +
                ", len2=" + len2 +
                ", val2='" + val2 + '\'' +
                '}';
    }
}
