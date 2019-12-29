package Checkers.Net.Wraps;

import Checkers.Objects.CheckerReverseData;

public class CheckerReverseWrap extends Wrap<CheckerReverseData> {
    @Override
    public byte getWrapSign() {
        return Wrap.CHECKER_REVERSE_SIGN;
    }

    @Override
    public CheckerReverseData unwrap_to(CheckerReverseData arg) {
        return null;
    }

    @Override
    public CheckerReverseData unwrap() throws WrongIntepretation {
        return null;
    }
}
