package Checkers.Net.Wraps;

import Checkers.Extensions.SmoothTransform;

import java.nio.ByteBuffer;

public class TransformWrap extends Wrap<SmoothTransform> {
    public TransformWrap(SmoothTransform from, int param){
        this.bytes = from.to_bytes(param);
    }

    public TransformWrap(byte[] bytes) throws WrongIntepretation{
        this.bytes = bytes;
        check_interpretation();
    }

    @Override
    public byte getWrapSign() {
        return Wrap.TRANSFORM_SIGN;
    }

    public int getParam(){
        return ByteBuffer.wrap(bytes).getInt(25);
    }

    @Override
    public SmoothTransform unwrap_to(SmoothTransform arg) {
        arg.from_bytes(bytes);
        return arg;
    }

    @Override
    public SmoothTransform unwrap() {
        return null;
    }
}
