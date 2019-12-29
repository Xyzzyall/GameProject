package Checkers.Net.Wraps;

public class MouseLeftRelease extends MouseActionWrap {
    public MouseLeftRelease(byte[] bytes) throws WrongIntepretation {
        super(bytes);
    }

    @Override
    public byte getWrapSign() {
        return Wrap.MOUSE_LEFT_RELEASE;
    }
}
