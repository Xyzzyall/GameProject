package Checkers.Net.Wraps;


public class MouseLeftClick extends MouseActionWrap {
    public MouseLeftClick(byte[] bytes) throws WrongIntepretation {
        super(bytes);
    }

    @Override
    public byte getWrapSign() {
        return Wrap.MOUSE_LEFT_CLICK;
    }
}
