package Checkers.Net.Wraps;

public class MouseMiddleClick extends MouseActionWrap {
    public MouseMiddleClick(byte[] bytes) throws WrongIntepretation {
        super(bytes);
    }

    @Override
    public byte getWrapSign() {
        return Wrap.MOUSE_MIDDLE_CLICK;
    }
}
