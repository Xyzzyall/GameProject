package Checkers.Menu;

import java.util.function.Consumer;

public abstract class Action implements Consumer<Action.Actions> {
    public enum Actions {
        SINGE_GAME,
        START_MULTIPLAYER_GAME,
        OPEN_SETTINGS,
        EXIT
    }
}
