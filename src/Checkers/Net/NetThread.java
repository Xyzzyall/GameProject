package Checkers.Net;

import Checkers.Extensions.SmoothTransform;
import Checkers.GameThread;
import Checkers.Net.Wraps.CheckerReverseWrap;
import Checkers.Net.Wraps.MouseActionWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Net.Wraps.Wrap;
import Checkers.Objects.MouseActionData;

import java.util.ArrayDeque;
import java.util.ArrayList;

public abstract class NetThread extends GameThread {

    private ArrayDeque<byte[]> given_packets = new ArrayDeque<>();
    private ArrayDeque<byte[]> packets_to_sent = new ArrayDeque<>();

    public NetThread(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }

    public NetThread(GameThread thread) {
        super(thread);
    }

    protected boolean sendingQueueIsEmpty(){
        return packets_to_sent.isEmpty();
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
    protected void to_send_queue(byte[] bytes){
        packets_to_sent.addLast(bytes);
    }

    protected byte[] pop_bytes_to_send(){
        System.out.println(packets_to_sent.size());
        return packets_to_sent.pollFirst();
    }

    static protected class WrapsUnpacker{
        public ArrayList<MouseActionWrap> mouseActionWraps;
        public ArrayList<TransformWrap> transformWraps;
        public ArrayList<CheckerReverseWrap> checkerReverseWraps;

        public WrapsUnpacker(NetThread thread){
            mouseActionWraps = new ArrayList<>();
            transformWraps = new ArrayList<>();
            checkerReverseWraps = new ArrayList<>();

            while (!thread.given_packets.isEmpty()){
                byte[] bytes = thread.pop();
                try {
                    switch (bytes[0]){
                        case Wrap.TRANSFORM_SIGN:
                            transformWraps.add(new TransformWrap(bytes));
                            break;
                        case Wrap.CHECKER_REVERSE_SIGN:
                            checkerReverseWraps.add(new CheckerReverseWrap(bytes));
                            break;
                        case Wrap.MOUSE_LEFT_CLICK:
                        case Wrap.MOUSE_LEFT_HOLD:
                        case Wrap.MOUSE_LEFT_RELEASE:
                        case Wrap.MOUSE_MIDDLE_CLICK:
                            mouseActionWraps.add(new MouseActionWrap(bytes));
                            break;
                    }
                } catch (Wrap.WrongIntepretation exc){
                    exc.printStackTrace();
                }
            }
        }

        public int length(){
            return mouseActionWraps.size() + transformWraps.size() + checkerReverseWraps.size();
        }
    }

    public abstract void acceptData(byte[] bytes);
    public abstract byte[] sendData();
}
