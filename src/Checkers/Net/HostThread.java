package Checkers.Net;

import Checkers.GameThread;
import Checkers.Net.DataHandlers.HostDataHandler;
import Checkers.Net.Desks.HostDesk;
import Checkers.Objects.Checker;
import Checkers.Objects.Desk;
import Main.Actor;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Queue;

public abstract class HostThread extends NetThread {
    HostDesk desk;
    HostDataHandler dataHandler;

    public HostThread(GameThread thread) {
        super(thread);
    }

    @Override
    protected void init() {
        super.init();
        try {
            Checker.loadTexures(game);
            desk = new HostDesk(game);
            game.map = desk;
        } catch(Exception e){e.printStackTrace(); throw new Error();}
        dataHandler = new HostDataHandler(desk);
        desk.addActor(dataHandler);
        desk.addActor(new Host(this, dataHandler));
    }

    private static class Host extends Actor{
        HostThread thread;
        HostDataHandler handler;

        public Host(HostThread thread, HostDataHandler handler){
            this.alive = true;
            this.thread = thread;
            this.handler = handler;
            /*try {
                thread.udp.init(false);
            } catch (SocketException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void update() {
            thread.desk.logicDesk.toTrainingMode();
            WrapsUnpacker wraps = new WrapsUnpacker(thread);
            handler.acceptWraps(wraps.mouseActionWraps, wraps.transformWraps, wraps.checkerReverseWraps);

            ArrayList<byte[]> checker_bytes = handler.getCheckerBytes();
            if (checker_bytes != null){
                for (byte[] bytes:
                     checker_bytes) {
                    try {
                        thread.udp.send(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void draw() {

        }
    }

    public HostThread(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }
}
