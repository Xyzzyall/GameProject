package Checkers.Objects;

import Checkers.Net.Wraps.Wrap;

import java.nio.ByteBuffer;

public class CheckerReverseData {
    public char checker_type;
    public CheckerReverseData(char checker_type){
        this.checker_type = checker_type;
    }

    public CheckerReverseData(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        checker_type = byteBuffer.getChar(1);
    }

    public byte[] to_bytes(int param){
        return ByteBuffer.allocate(2 + 4).put(Wrap.CHECKER_REVERSE_SIGN).putChar(checker_type).putInt(param).array();
    }
}
