package Checkers.Net.Desks;

import Checkers.Objects.Checker;
import Checkers.Objects.Desk;
import Main.Game;
import Main.TextureBank;

public class ClientDesk extends Desk {

    public ClientDesk(Game game) throws TextureBank.NonExistentTextureException {
        super(game, false);
    }



    @Override
    public void update() {
        super.update();

    }
}
