package Checkers.Net.Wraps;

import Checkers.Objects.MouseActionData;

public class MouseLeftClick extends Wrap<MouseActionData> {
    @Override
    public byte getWrapSign() {
        return 0;
    }

    @Override
    public MouseActionData unwrap_to(MouseActionData arg) {
        return null;
    }
}
