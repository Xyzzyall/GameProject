package Checkers.Net;

import Checkers.Extensions.SmoothTransform;
import Checkers.GameThread;
import Checkers.Net.Wraps.MouseActionWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Net.Wraps.Wrap;
import Checkers.Objects.MouseActionData;

import java.util.ArrayDeque;

public abstract class NetThread extends GameThread {

    private ArrayDeque<byte[]> given_packets = new ArrayDeque<>();
    private ArrayDeque<byte[]> packets_to_sent = new ArrayDeque<>();

    public NetThread(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }

    private byte[] pop(){
        return given_packets.pollLast();
    }

    private byte[] last(){
        return given_packets.getLast();
    }

    protected void pull(byte[] packet){
        given_packets.addFirst(packet);
    }

    protected void to_send_queue(Wrap wrap){
        packets_to_sent.addFirst(wrap.bytes);
    }

    protected byte[] pop_bytes_to_send(){
        return packets_to_sent.pollLast();
    }

    protected SmoothTransform tryUnwrapToTransform(SmoothTransform arg){
        try {
            TransformWrap res = new TransformWrap(last());
            pop();
            return res.unwrap_to(arg);
        } catch (Wrap.WrongIntepretation exc){
            return null;
        }
    }

    protected MouseActionData tryUnwrapToMouseAction(){
        try {
            MouseActionWrap res = new MouseActionWrap(last());
            pop();
            return res.unwrap();
        } catch (Wrap.WrongIntepretation exc){
            return null;
        }
    }
}
