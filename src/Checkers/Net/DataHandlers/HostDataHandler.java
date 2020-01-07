package Checkers.Net.DataHandlers;

import Checkers.Net.Desks.HostDesk;
import Checkers.Net.Wraps.CheckerReverseWrap;
import Checkers.Net.Wraps.MouseActionWrap;
import Checkers.Net.Wraps.TransformWrap;
import Checkers.Net.Wraps.Wrap;
import Checkers.Objects.Checker;
import Checkers.Objects.CheckerReverseData;
import Checkers.Objects.MouseActionData;
import Main.Actor;
import org.joml.Vector2i;

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

    private Vector2i clientFrom;

    private void setCheckerOnDesk(Checker c, ArrayList<MouseActionWrap> mouseActionWraps){
        for (MouseActionWrap wrap:
                mouseActionWraps) {
            MouseActionData data = new MouseActionData(wrap.bytes);
            switch (data.type){
                case Wrap.MOUSE_LEFT_CLICK:
                    clientFrom = desk.getClosestCell(c.smoothTransform);
                    break;
                case Wrap.MOUSE_LEFT_HOLD:
                    break;
                case Wrap.MOUSE_LEFT_RELEASE:
                    Vector2i cell = desk.getClosestCell(c.smoothTransform);
                    desk.setCheckerOnCell(c, cell.x, cell.y);
                    desk.turn(clientFrom, cell);

                    for (Checker checker:
                            desk.getCheckers()) {
                        Vector2i vec = desk.getClosestCell(checker.transform);
                        if ( vec!=null && desk.logicDesk.get(vec.x, vec.y) == '*'){
                            desk.killChecker(checker);
                            desk.logicDesk.set(vec.x, vec.y, ' ');
                        }
                    }
                    break;
                case Wrap.MOUSE_MIDDLE_CLICK:
                    break;
            }
        }
    }

    public void acceptWraps(ArrayList<MouseActionWrap> mouseActionWraps, ArrayList<TransformWrap> transformWraps, ArrayList<CheckerReverseWrap> checkerReverseWraps){

        for (TransformWrap wrap:
             transformWraps) {
            int i = wrap.getParam();
            Checker checker = desk.getCheckers().get(i);
            if (checker != desk.getCurrentChecker()){
                wrap.unwrap_to(checker.smoothTransform);
                setCheckerOnDesk(checker, mouseActionWraps);
            }
        }
        for (CheckerReverseWrap wrap:
            checkerReverseWraps){
            int i = wrap.getParam();
            Checker checker = desk.getCheckers().get(i);
            try {
                CheckerReverseData reverse = wrap.unwrap();
                if (checker != desk.getCurrentChecker()){
                    checker.changeTyp(reverse.checker_type);
                }
            } catch (Wrap.WrongIntepretation wrongIntepretation) {
                wrongIntepretation.printStackTrace();
            }
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
