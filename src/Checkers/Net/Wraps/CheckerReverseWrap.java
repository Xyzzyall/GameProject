package Checkers.Net.Wraps;

import Checkers.Objects.CheckerReverseData;

import java.nio.ByteBuffer;

public class CheckerReverseWrap extends Wrap<CheckerReverseData> {
    public CheckerReverseWrap(CheckerReverseData data, int param){
        bytes = data.to_bytes(param);
    }

    @Override
    public byte getWrapSign() {
        return Wrap.CHECKER_REVERSE_SIGN;
    }

    public int getParam(){
        return ByteBuffer.wrap(bytes).getInt(3);
    }

    @Override
    public CheckerReverseData unwrap_to(CheckerReverseData arg) {
        arg = new CheckerReverseData(bytes);
        return arg;
    }

    @Override
    public CheckerReverseData unwrap() throws WrongIntepretation {
        return new CheckerReverseData(bytes);
    }
}
