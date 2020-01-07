package Checkers.Menu;

import java.util.function.Consumer;

public abstract class Action implements Consumer<Action.Actions> {
    public enum Actions {
        SINGLE_GAME,
        START_MULTIPLAYER_GAME,
        OPEN_SETTINGS,
        EXIT,
        BACK_TO_MAIN_MENU,
        CONNECT_TO_SERVER,
        CREATE_HOST
    }


}
