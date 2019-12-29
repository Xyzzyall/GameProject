package Checkers.Net.Wraps;

import Checkers.Objects.MouseActionData;

public class MouseActionWrap extends Wrap<MouseActionData> {
    public MouseActionWrap(byte[] bytes) throws WrongIntepretation{
        this.bytes = bytes;
        check_interpretation();
    }

    @Override
    public byte getWrapSign() {
        return 0;
    }

    @Override
    public void check_interpretation() throws WrongIntepretation {
        byte sign = bytes[0];
        switch (sign) {
            case Wrap.MOUSE_LEFT_CLICK:
            case Wrap.MOUSE_LEFT_HOLD:
            case Wrap.MOUSE_LEFT_RELEASE:
            case Wrap.MOUSE_MIDDLE_CLICK:
                return;
        }
        throw new WrongIntepretation();
    }

    @Override
    public MouseActionData unwrap_to(MouseActionData arg) throws WrongIntepretation{
        throw new WrongIntepretation();
    }

    @Override
    public MouseActionData unwrap() {
        return new MouseActionData(bytes);
    }
}
