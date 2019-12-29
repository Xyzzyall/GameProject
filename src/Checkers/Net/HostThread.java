package Checkers.Net;

import Checkers.GameThread;
import Checkers.Net.DataHandlers.HostDataHandler;
import Checkers.Net.Desks.HostDesk;
import Checkers.Objects.Checker;
import Checkers.Objects.Desk;
import Main.Actor;

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
        }

        @Override
        public void update() {
            ArrayList<byte[]> checker_bytes = handler.getCheckerBytes();
            if (checker_bytes != null){
                System.out.println(checker_bytes.size());
                for (byte[] bytes:
                     checker_bytes) {
                    thread.to_send_queue(bytes);
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
