package Checkers.Net;

import Checkers.GameThread;
import Checkers.Net.DataHandlers.ClientDataHandler;
import Checkers.Net.Desks.ClientDesk;
import Checkers.Objects.Checker;
import Main.Actor;

import java.net.SocketException;

public abstract class ClientThread extends NetThread {
    ClientDesk desk;
    ClientDataHandler dataHandler;

    public ClientThread(GameThread thread) {
        super(thread);
    }

    @Override
    protected void init() {
        super.init();
        try {
            Checker.loadTexures(game);
            desk = new ClientDesk(game);
            game.map = desk;
        } catch(Exception e){e.printStackTrace(); throw new Error();}
        dataHandler = new ClientDataHandler(desk);
        desk.addActor(dataHandler);
        desk.addActor(new Client(this, dataHandler));
    }

    static private class Client extends Actor{
        ClientThread thread;
        ClientDataHandler handler;

        public Client(ClientThread thread, ClientDataHandler dataHandler){
            this.alive = true;
            this.thread = thread;
            this.handler = dataHandler;
            try {
                thread.udp.init();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update() {
            WrapsUnpacker wraps = new WrapsUnpacker(thread);
            if (wraps.length() > 0)
                handler.acceptWraps(wraps.transformWraps, wraps.checkerReverseWraps);
        }

        @Override
        public void draw() {

        }
    }

    public ClientThread(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }
}
