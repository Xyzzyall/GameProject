package Checkers.Net.DataHandlers;

import Checkers.Net.Desks.HostDesk;
import Checkers.Net.Wraps.CheckerReverseWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Objects.Checker;
import Checkers.Objects.CheckerReverseData;
import Main.Actor;

import java.util.ArrayList;
import java.util.Vector;

public class HostDataHandler extends Actor {
    HostDesk desk;

    public HostDataHandler(HostDesk desk){
        this.desk = desk;
        this.alive = true;
    }

    static int SENDING_FREQUENCY = 10;

    private ArrayList<byte[]> checker_bytes = new ArrayList<>();

    public ArrayList<byte[]> getCheckerBytes(){
        if (!checker_bytes.isEmpty()){
            ArrayList<byte[]> res = new ArrayList<>(checker_bytes);
            checker_bytes.clear();
            return res;
        } else {
            return null;
        }
    }

    private int tick = 0;
    @Override
    public void update() {
        if (tick == SENDING_FREQUENCY){
            checker_bytes.clear();
            Vector<Checker> checkers = desk.getCheckers();
            int n = checkers.size();
            for (int i = 0; i < n; i++){
                Checker c = checkers.get(i);
                checker_bytes.add(new TransformWrap(c.smoothTransform, i).bytes);
                checker_bytes.add(new CheckerReverseWrap(new CheckerReverseData(c.typ), i).bytes);
            }
            tick = 0;
        } else {
            tick += 1;
        }
    }

    @Override
    public void draw() {

    }
}
