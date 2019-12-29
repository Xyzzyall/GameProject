package Checkers.Net.Desks;

import Checkers.Objects.Desk;
import Main.Game;
import Main.TextureBank;

public class ClientDesc extends Desk {

    public ClientDesc(Game game, boolean buttons) throws TextureBank.NonExistentTextureException {
        super(game, buttons);
    }
}
