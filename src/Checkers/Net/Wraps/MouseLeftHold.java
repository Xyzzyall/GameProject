package Checkers.Net.Wraps;

public class MouseLeftHold extends MouseActionWrap {
    public MouseLeftHold(byte[] bytes) throws WrongIntepretation {
        super(bytes);
    }

    @Override
    public byte getWrapSign() {
        return Wrap.MOUSE_LEFT_HOLD;
    }
}
