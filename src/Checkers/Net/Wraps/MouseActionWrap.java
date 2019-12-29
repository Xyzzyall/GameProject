package Checkers.Net.Wraps;

import Checkers.Objects.MouseActionData;

public abstract class MouseActionWrap extends Wrap<MouseActionData> {
    public MouseActionWrap(byte[] bytes) throws WrongIntepretation{
        this.bytes = bytes;
        check_interpretation();
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
