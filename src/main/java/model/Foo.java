package model;

public class Foo extends BaseFoo {
    private final String val1;
    private final String val2;

    public Foo(String val1, String val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public String getVal1() {
        return val1;
    }

    public String getVal2() {
        return val2;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "val1='" + val1 + '\'' +
                ", val2='" + val2 + '\'' +
                '}';
    }
}
