package Checkers.Net;

import Checkers.Extensions.SmoothTransform;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Net.Wraps.Wrap;

import java.util.ArrayDeque;

public abstract class NetThread extends Thread {

    private ArrayDeque<byte[]> given_packets = new ArrayDeque<>();
    private ArrayDeque<byte[]> packets_to_sent = new ArrayDeque<>();
    private byte[] pop(){
        return given_packets.pollLast();
    }

    private byte[] last(){
        return given_packets.getLast();
    }

    private void pull(byte[] packet){
        given_packets.addFirst(packet);
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
}
