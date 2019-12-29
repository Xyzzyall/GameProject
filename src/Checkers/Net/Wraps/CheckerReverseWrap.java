package Checkers.Net.Wraps;

import Checkers.Objects.CheckerReverseData;

public class CheckerReverseWrap extends Wrap<CheckerReverseData> {
    @Override
    public byte getWrapSign() {
        return 0;
    }

    @Override
    public CheckerReverseData unwrap_to(CheckerReverseData arg) {
        return null;
    }
}
