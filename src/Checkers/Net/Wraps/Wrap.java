package Checkers.Net.Wraps;

public abstract class Wrap<T> {
    public static final byte TRANSFORM_SIGN = 1,
                             MOUSE_LEFT_CLICK = 2,
                             MOUSE_LEFT_HOLD = 3,
                             MOUSE_LEFT_RELEASE = 4,
                             MOUSE_MIDDLE_CLICK = 5,
                             CHECKER_REVERSE_SIGN = 6,
                             CHECKER_GRABBED = 7,
                             CHECKER_HOLD = 8,
                             CHECKER_SET = 9,
                             ECHO_PACKET = 10, ECHO_RESPONSE = 11;

    public abstract byte getWrapSign();

    public byte[] bytes;

    public abstract T unwrap_to(T arg) throws WrongIntepretation;

    public abstract T unwrap() throws WrongIntepretation;

    public void check_interpretation() throws WrongIntepretation{
        if(bytes[0] != getWrapSign()){
            throw new WrongIntepretation();
        }
    }

    public static class WrongIntepretation extends Exception{

    }

}


