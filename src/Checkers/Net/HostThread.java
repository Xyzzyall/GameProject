package Checkers.Net;

import Checkers.Net.DataHandlers.HostDataHandler;
import Checkers.Net.Desks.HostDesk;
import Checkers.Objects.Checker;
import Checkers.Objects.Desk;

import java.util.Queue;

public class HostThread extends NetThread {
    HostDesk desk;
    HostDataHandler dataHandler;

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
    }

    public HostThread(int width, int height, int fps, String[] textures) {
        super(width, height, fps, textures);
    }
}
