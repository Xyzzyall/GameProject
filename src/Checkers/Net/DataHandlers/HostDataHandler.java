package Checkers.Net.DataHandlers;

import Checkers.Net.Desks.HostDesk;
import Checkers.Net.Wraps.CheckerReverseWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Objects.Checker;
import Checkers.Objects.CheckerReverseData;
import Main.Actor;

import java.util.ArrayList;

public class HostDataHandler extends Actor {
    HostDesk desk;

    public HostDataHandler(HostDesk desk){
        this.desk = desk;
        this.alive = true;
    }

    static int SENDING_FREQUENCY = 10;

    private ArrayList<byte[]> checker_bytes = new ArrayList<>();
    private boolean wrapsReady = false;

    public ArrayList<byte[]> getChecker_bytes(){
        if (wrapsReady){
            ArrayList<byte[]> res = new ArrayList<>(checker_bytes);
            checker_bytes.clear();
            wrapsReady = false;
            return res;
        } else {
            return null;
        }
    }

    private int tick = 0;
    @Override
    public void update() {
        if (tick == SENDING_FREQUENCY){
            wrapsReady = false;
            checker_bytes.clear();
            for (Checker c:
                    desk.getCheckers()) {
                checker_bytes.add(new TransformWrap(c.smoothTransform).bytes);
                checker_bytes.add(new CheckerReverseWrap(new CheckerReverseData(c.typ)).bytes);
            }
            wrapsReady = true;
            tick += 1;
        } else {
            tick = 0;
        }
    }

    @Override
    public void draw() {

    }
}
