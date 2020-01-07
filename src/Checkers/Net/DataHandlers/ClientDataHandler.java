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

    public boolean wraps_accepted = true;

    public boolean acceptWraps(ArrayList<TransformWrap> transforms, ArrayList<CheckerReverseWrap> reverses){
        if (wraps_accepted) {
            checker_transform_wraps = transforms;
            checker_reverse_wraps = reverses;
            wraps_accepted = false;
        }
        return true;
    }

    @Override
    public void update() {
        for (TransformWrap transformWrap:
             checker_transform_wraps) {
            int i = transformWrap.getParam();
            Checker checker = checkers.get(i);
            if (checker != desk.getCurrentChecker()){
                transformWrap.unwrap_to(checkers.get(i).smoothTransform);
            }
        }
        for (CheckerReverseWrap reverses:
             checker_reverse_wraps) {
            int i = reverses.getParam();
            Checker c = checkers.get(i);
            try {
                CheckerReverseData reverse = reverses.unwrap();
                if (c != desk.getCurrentChecker()) {
                    c.changeTyp(reverse.checker_type);
                }
            } catch (Wrap.WrongIntepretation ex) {
                ex.printStackTrace();
            }
        }
        wraps_accepted = true;
    }

    @Override
    public void draw() {

    }
}
