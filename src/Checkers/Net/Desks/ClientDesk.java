package Checkers.Net.Desks;

import Checkers.Net.Wraps.MouseActionWrap;
import Checkers.Net.Wraps.MouseLeftClick;
import Checkers.Objects.Checker;
import Checkers.Objects.Desk;
import Checkers.Objects.MouseActionData;
import Main.Game;
import Main.TextureBank;
import org.joml.Vector2f;

import java.util.ArrayList;

public class ClientDesk extends Desk {

    public ClientDesk(Game game) throws TextureBank.NonExistentTextureException {
        super(game, false);
    }

    private ArrayList<MouseActionWrap> mouse_wraps = new ArrayList<>();
    private boolean data_accepted = false;

    public ArrayList<byte[]> getMouseBytes(){
        if (data_accepted){
            return null;
        }
        ArrayList<byte[]> res = new ArrayList<>();
        for (MouseActionWrap wrap:
             mouse_wraps) {
            res.add(wrap.bytes.clone());
        }
        data_accepted = true;
        return res;
    }

    private final int HOLD_SENDING_FREQUENCY = 3;
    private int hold = 0;
    @Override
    public void mouseLeftHold(Vector2f mouse_pos, Vector2f last_pos) {
        super.mouseLeftHold(mouse_pos, last_pos);
        if (hold > HOLD_SENDING_FREQUENCY){
            mouse_wraps.add(new MouseActionWrap(new MouseActionData(MouseActionWrap.MOUSE_LEFT_HOLD, mouse_pos, getLastMousePos())));
            hold = 0;
        } else {
            hold += 1;
        }

    }

    @Override
    public void mouseLeftPress(Vector2f mouse_pos) {
        super.mouseLeftPress(mouse_pos);
        mouse_wraps.add(new MouseActionWrap(new MouseActionData(MouseActionWrap.MOUSE_LEFT_CLICK, mouse_pos, getLastMousePos())));
    }

    @Override
    public void mouseLeftRelease() {
        super.mouseLeftRelease();
        mouse_wraps.add(new MouseActionWrap(new MouseActionData(MouseActionWrap.MOUSE_LEFT_HOLD, getLastMousePos(), getLastMousePos())));
        mouse_wraps.add(new MouseActionWrap(new MouseActionData(MouseActionWrap.MOUSE_LEFT_RELEASE, new Vector2f(0,0), getLastMousePos())));
    }

    @Override
    public void mouseMiddlePress() {
        super.mouseMiddlePress();
        mouse_wraps.add(new MouseActionWrap(new MouseActionData(MouseActionWrap.MOUSE_MIDDLE_CLICK, new Vector2f(0,0), getLastMousePos())));
    }

    @Override
    public void update() {
        super.update();
        if (data_accepted){
            mouse_wraps.clear();
            data_accepted = false;
        }
    }
}
