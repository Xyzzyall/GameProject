package Checkers.Net.Desks;


import Checkers.Objects.Checker;
import Checkers.Objects.Desk;
import Main.Game;
import Main.TextureBank;

import java.util.Vector;

public class HostDesk extends Desk {
    public HostDesk(Game game) throws TextureBank.NonExistentTextureException {
        super(game, true);
    }

    public Vector<Checker> getCheckers(){
        return checkers;
    }
}
