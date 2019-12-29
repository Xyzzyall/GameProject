package Checkers.Net.Wraps;

import Checkers.Extensions.SmoothTransform;

public class TransformWrap extends Wrap<SmoothTransform> {
    public TransformWrap(SmoothTransform from){
        this.bytes = from.to_bytes();
    }

    public TransformWrap(byte[] bytes) throws WrongIntepretation{
        this.bytes = bytes;
        if (bytes[0] != Wrap.TRANSFORM_SIGN){
            throw new Wrap.WrongIntepretation();
        }
    }

    @Override
    public byte getWrapSign() {
        return Wrap.TRANSFORM_SIGN;
    }

    @Override
    public SmoothTransform unwrap_to(SmoothTransform arg) {
        arg.from_bytes(bytes);
        return arg;
    }

}
