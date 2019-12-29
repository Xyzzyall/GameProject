package Checkers.Net.DataHandlers;

import Checkers.Net.Desks.ClientDesk;
import Checkers.Net.Wraps.CheckerReverseWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Net.Wraps.Wrap;
import Checkers.Objects.Checker;
import Checkers.Objects.CheckerReverseData;
import Main.Actor;

import java.util.ArrayList;
import java.util.Vector;

public class ClientDataHandler extends Actor {
    ClientDesk desk;
    Vector<Checker> checkers;
    public ClientDataHandler(ClientDesk desk){
        this.desk = desk;
        this.alive = true;
        checkers = desk.getCheckers();
    }

    private ArrayList<TransformWrap> checker_transform_wraps = new ArrayList<>();
    private ArrayList<CheckerReverseWrap> checker_reverse_wraps = new ArrayList<>();
    private boolean wrapsReady = false;

    public boolean acceptWraps(ArrayList<TransformWrap> transforms, ArrayList<CheckerReverseWrap> reverses){
        if (!wrapsReady) {
            checker_transform_wraps = new ArrayList<>(transforms);
            checker_reverse_wraps = new ArrayList<>(reverses);
            wrapsReady = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update() {
        if(wrapsReady){
            for (TransformWrap transformWrap:
                 checker_transform_wraps) {
                int i = transformWrap.getParam();
                transformWrap.unwrap_to(checkers.get(i).smoothTransform);
            }
            for (CheckerReverseWrap reverses:
                 checker_reverse_wraps) {
                int i = reverses.getParam();
                Checker c = checkers.get(i);
                try {
                    CheckerReverseData reverse = reverses.unwrap();
                    c.changeTyp(reverse.checker_type);
                } catch (Wrap.WrongIntepretation ex) {
                    ex.printStackTrace();
                }
            }
            wrapsReady = false;
        }

    }

    @Override
    public void draw() {

    }
}
