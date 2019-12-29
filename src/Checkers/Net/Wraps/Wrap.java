package Checkers.Net.Wraps;

public abstract class Wrap<T> {
    public static final byte TRANSFORM_SIGN = 1;

    public abstract byte getWrapSign();

    public byte[] bytes;

    public abstract T unwrap_to(T arg);

    public static class WrongIntepretation extends Exception{

    }
}


