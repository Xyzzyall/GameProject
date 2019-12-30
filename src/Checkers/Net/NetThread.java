package Checkers.Net;

import Checkers.Extensions.SmoothTransform;
import Checkers.GameThread;
import Checkers.Net.Wraps.CheckerReverseWrap;
import Checkers.Net.Wraps.MouseActionWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Net.Wraps.Wrap;
import Checkers.Objects.MouseActionData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Vector;

public abstract class NetThread extends GameThread {

    public NetThread(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }

    public NetThread(GameThread thread) {
        super(thread);
    }

    public void setUdp(InetAddress self, int port, InetAddress other, int otherPort) throws SocketException {
        udp = new UDP(self, port, other, otherPort);
    }

    Vector<byte[]> receiveAll(){
        Vector<byte[]> res = new Vector<>();
        while (true){
            try {
                res.add(udp.receive());
            } catch (Exception e) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public UDP udp;

    static protected class WrapsUnpacker{
        public ArrayList<MouseActionWrap> mouseActionWraps;
        public ArrayList<TransformWrap> transformWraps;
        public ArrayList<CheckerReverseWrap> checkerReverseWraps;

        public WrapsUnpacker(NetThread thread){
            mouseActionWraps = new ArrayList<>();
            transformWraps = new ArrayList<>();
            checkerReverseWraps = new ArrayList<>();

            for (byte[] bytes:
                 thread.receiveAll()) {
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

        @Override
        public String toString() {
            return "WrapsUnpacker{" +
                    "mouseActionWraps=" + mouseActionWraps.size()+
                    ", transformWraps=" + transformWraps.size() +
                    ", checkerReverseWraps=" + checkerReverseWraps.size() +
                    '}';
        }
    }
}
